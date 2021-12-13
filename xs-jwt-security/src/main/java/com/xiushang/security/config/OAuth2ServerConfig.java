package com.xiushang.security.config;


import com.xiushang.framework.log.SecurityConstants;
import com.xiushang.security.filter.CustomClientCredentialsTokenEndpointFilter;
import com.xiushang.security.granter.*;
import com.xiushang.security.hadler.*;
import com.xiushang.security.token.CustomTokenEnhancer;
import com.xiushang.security.token.CustomTokenExtractor;
import com.xiushang.security.token.CustomUserAuthenticationConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class OAuth2ServerConfig {

    private static final String RESOURCE_ID = "oauth2";

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Autowired
        CustomTokenExtractor customTokenExtractor;
        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            // 如果关闭 stateless，则 accessToken 使用时的 session id 会被记录，后续请求不携带 accessToken 也可以正常响应
            // 如果 stateless 为 true 打开状态，则 每次请求都必须携带 accessToken 请求才行，否则将无法访问
            resources.resourceId(RESOURCE_ID).stateless(true);

            //oauth/token 异常处理
            resources.authenticationEntryPoint(new ResourceExceptionEntryPoint());
            resources.accessDeniedHandler(new ResourceAccessDeniedHandler());
            resources.tokenExtractor(customTokenExtractor);

        }

        /**
         * 为oauth2单独创建角色，这些角色只具有访问受限资源的权限，可解决token失效的问题
         * @param http
         * @throws Exception
         */
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                // 获取登录用户的 session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                    // 资源服务器拦截的路径 注意此路径不要和主过滤器冲突
                    .requestMatchers().antMatchers("/api/**");
            //
            http
                .authorizeRequests()
                     // 配置资源服务器已拦截的路径才有效
                    .antMatchers("/api/**").authenticated();
                    // .access(" #oauth2.hasScope('select') or hasAnyRole('ROLE_超级管理员', 'ROLE_设备管理员')");
            //
            http
                    .exceptionHandling()//.accessDeniedHandler(new OAuth2AccessDeniedHandler())
                    .authenticationEntryPoint(new SecurityAuthenticationEntryPoint())
                    .accessDeniedHandler(new SecurityAccessDeniedHandler())
                    .and()
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated()
            ;
        }
    }


    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        private AuthenticationManager authenticationManager;
        @Autowired
        private DataSource dataSource;
        @Autowired
        @Qualifier("userDetailsServiceImpl")
        private UserDetailsService userDetailsService;
        @Autowired
        private ClientDetailsService clientDetailsService;
        @Autowired
        private AuthorizationCodeServices authorizationCodeServices;
        @Autowired
        private PasswordEncoder passwordEncoder;
        // @Autowired
        // private RedisConnectionFactory redisConnectionFactory;

        @Autowired
        private StringRedisTemplate stringRedisTemplate;
        /**
         * ClientDetails实现
         *
         * @return
         */
        @Bean
        public ClientDetailsService clientDetails() {
            return new JdbcClientDetailsService(dataSource);
        }

        @Bean
        public TokenStore tokenStore() {
            //return new JdbcTokenStore(dataSource);

            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        // @Bean
        // public TokenStore redisTokenStore() {
        //     return new RedisTokenStore(redisConnectionFactory);
        // }

        /**
         * 加入对授权码模式的支持
         * @param dataSource
         * @return
         */
        @Bean
        public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
            return new JdbcAuthorizationCodeServices(dataSource);
        }

        // @Bean
        // public ApprovalStore approvalStore() {
        //     TokenApprovalStore store = new TokenApprovalStore();
        //     store.setTokenStore(tokenStore());
        //     return store;
        // }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            // 1. 数据库的方式
            clients.withClientDetails(clientDetails());
        }


        /**
         * 程序支持的授权类型
         * 参考 AuthorizationServerEndpointsConfigurer 默认配置
         * @return
         */
        private List<TokenGranter> getDefaultTokenGranters(AuthorizationServerEndpointsConfigurer endpoints) {
            ClientDetailsService clientDetails = endpoints.getClientDetailsService();
            AuthorizationServerTokenServices tokenServices = endpoints.getTokenServices();
            OAuth2RequestFactory requestFactory = endpoints.getOAuth2RequestFactory();

            List<TokenGranter> tokenGranters = new ArrayList<>();
            // 添加授权码模式
            tokenGranters.add(new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, clientDetails,
                    requestFactory));
            // 添加刷新令牌的模式
            tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetails, requestFactory));
            // 添加隐式授权模式 ImplicitTokenGranter
            CustomImplicitTokenGranter implicit = new CustomImplicitTokenGranter(tokenServices, clientDetails, requestFactory);
            tokenGranters.add(implicit);
            // 添加客户端模式 使用自定义客户端模式  ClientCredentialsTokenGranter
            tokenGranters.add(new CustomClientCredentialsTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                    endpoints.getOAuth2RequestFactory(), authenticationManager
            ));
            if (authenticationManager != null) {
                // 添加密码模式
                tokenGranters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices,
                        clientDetails, requestFactory));
            }
            return tokenGranters;
        }
        /**
         * 声明授权和token的端点以及token的服务的一些配置信息，
         * 比如采用什么存储方式、token的有效期等
         * @param endpoints
         */
        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) {

            // 获取原有默认授权模式(授权码模式、密码模式、客户端模式、简化模式)的授权者
            List<TokenGranter> granterList = getDefaultTokenGranters(endpoints);

            // 添加验证码授权模式授权者
            granterList.add(new CaptchaTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                    endpoints.getOAuth2RequestFactory(), authenticationManager, stringRedisTemplate
            ));
            // 添加手机短信验证码授权模式的授权者
            granterList.add(new SmsCodeTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                    endpoints.getOAuth2RequestFactory(), authenticationManager
            ));
            // 添加社交账号授权模式的授权者
            granterList.add(new SocialTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                    endpoints.getOAuth2RequestFactory(), authenticationManager
            ));
            // 添加微信code授权模式的授权者
            granterList.add(new WechatTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                    endpoints.getOAuth2RequestFactory(), authenticationManager
            ));

            CompositeTokenGranter compositeTokenGranter = new CompositeTokenGranter(granterList);

            //令牌增强器。将增强的token设置到增强链中
            TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
            tokenEnhancers.add(customTokenEnhancer());
            tokenEnhancers.add(jwtAccessTokenConverter());
            tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);

            TokenStore tokenStore = tokenStore();

            // 数据库管理授权信息
            //ApprovalStore approvalStore = new JdbcApprovalStore(dataSource);
            //endpoints.approvalStore(approvalStore);

            endpoints.tokenStore(tokenStore)
                    .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)  //oauth/token 请求支持GET POST
                    .authenticationManager(authenticationManager)
                    .userDetailsService(userDetailsService)
                    .authorizationCodeServices(authorizationCodeServices)
                    .setClientDetailsService(clientDetailsService)


            ;
            endpoints.tokenGranter(compositeTokenGranter);            //自定义授权方式
            endpoints.tokenServices(tokenServices(endpoints));
            endpoints.accessTokenConverter(jwtAccessTokenConverter());
            endpoints.tokenEnhancer(tokenEnhancerChain);
        }

        /**
         *  JWT编码的令牌值和OAuth身份验证信息之间进行转换
         *  自定义 authentication.getPrincipal() 方法返回 SecurityUser 对象
         * @return
         */
        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter(){

            //自定义用户转换器
            CustomUserAuthenticationConverter customUserAuthenticationConverter = new CustomUserAuthenticationConverter();
            customUserAuthenticationConverter.setUserDetailsService(userDetailsService);

            DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter();
            defaultAccessTokenConverter.setUserTokenConverter(customUserAuthenticationConverter);

            JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
            jwtAccessTokenConverter.setAccessTokenConverter(defaultAccessTokenConverter); // IMPORTANT
            jwtAccessTokenConverter.setSigningKey(SecurityConstants.SIGNING_KEY);
            return jwtAccessTokenConverter;
        }

        /**
         * 令牌增强器
         * @return
         */
        @Bean
        public TokenEnhancer customTokenEnhancer() {
            return new CustomTokenEnhancer();
        }


        public DefaultTokenServices tokenServices(AuthorizationServerEndpointsConfigurer endpoints) {
            TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
            tokenEnhancers.add(customTokenEnhancer());
            tokenEnhancers.add(jwtAccessTokenConverter());
            tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);

            DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setTokenStore(endpoints.getTokenStore());
            tokenServices.setSupportRefreshToken(true);
            tokenServices.setClientDetailsService(clientDetailsService);
            tokenServices.setTokenEnhancer(tokenEnhancerChain);
            // tokenServices.setAccessTokenValiditySeconds(60 * 3);   //token有效期自定义设置，默认12小时
            // tokenServices.setRefreshTokenValiditySeconds(60 * 60);  //默认30天，这里修改

            // 多用户体系下，刷新token再次认证客户端ID和 UserDetailService 的映射Map
           /* Map<String, UserDetailsService> clientUserDetailsServiceMap = new HashMap<>();
            clientUserDetailsServiceMap.put(SecurityConstants.ADMIN_CLIENT_ID, sysUserDetailsService); // 系统管理客户端
            clientUserDetailsServiceMap.put(SecurityConstants.APP_CLIENT_ID, memberUserDetailsService); // Android、IOS、H5 移动客户端
            clientUserDetailsServiceMap.put(SecurityConstants.WEAPP_CLIENT_ID, memberUserDetailsService); // 微信小程序客户端

            // 刷新token模式下，重写预认证提供者替换其AuthenticationManager，可自定义根据客户端ID和认证方式区分用户体系获取认证用户信息
            PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
            provider.setPreAuthenticatedUserDetailsService(new PreAuthenticatedUserDetailsService<>(clientUserDetailsServiceMap));
            tokenServices.setAuthenticationManager(new ProviderManager(Arrays.asList(provider)));

            //添加预身份验证
            if (userDetailsService != null) {
                PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
                provider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper<>(userDetailsService));
                tokenServices.setAuthenticationManager(new ProviderManager(Arrays.asList(provider)));
            }*/
            return tokenServices;

        }

        /**
         * 声明安全约束，哪些允许访问，哪些不允许访问
         * @param oauthServer
         */
        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
            /*//允许表单认证
            oauthServer.allowFormAuthenticationForClients();
            oauthServer.passwordEncoder(passwordEncoder);
            // 对于CheckEndpoint控制器[框架自带的校验]的/oauth/check端点允许所有客户端发送器请求而不会被Spring-security拦截
            oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
            oauthServer.realm("oauth2");*/

            //注意重写invalid_client错误返回时 不再需要配置allowFormAuthenticationForClients()。否则会被默认ClientCredentialsTokenEndpointFilter覆盖。
            CustomClientCredentialsTokenEndpointFilter endpointFilter = new CustomClientCredentialsTokenEndpointFilter(oauthServer);
            endpointFilter.afterPropertiesSet();
            endpointFilter.setAuthenticationEntryPoint(new CustomAuthenticationEntryPoint());
            // 客户端认证之前的过滤器
            oauthServer.addTokenEndpointAuthenticationFilter(endpointFilter);
            oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
            oauthServer.realm("oauth2");
        }
    }
}
