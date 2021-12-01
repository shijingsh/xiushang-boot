package com.xiushang.security.config;


import com.xiushang.framework.log.SecurityConstants;
import com.xiushang.security.granter.CaptchaTokenGranter;
import com.xiushang.security.granter.SmsCodeTokenGranter;
import com.xiushang.security.hadler.AuthExceptionEntryPoint;
import com.xiushang.security.hadler.SecurityAccessDeniedHandler;
import com.xiushang.security.hadler.SecurityAuthenticationEntryPoint;
import com.xiushang.security.token.CustomTokenEnhancer;
import com.xiushang.security.token.CustomTokenExtractor;
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
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lirong
 * Date 2019-3-18 09:04:36
 */
@Configuration
public class OAuth2ServerConfig {

    private static final String RESOURCE_ID = "oauth2";

    /**
     * 对外提供接口的资源服务，也就是被保护的对象。
     */
    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Autowired
        OAuth2UrlConfig oAuth2UrlConfig;

        @Autowired
        CustomTokenExtractor customTokenExtractor;

        @Autowired
        AuthExceptionEntryPoint authExceptionEntryPoint;

        @Autowired
        SecurityAccessDeniedHandler customAccessDeniedHandler;
        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            // 如果关闭 stateless，则 accessToken 使用时的 session id 会被记录，后续请求不携带 accessToken 也可以正常响应
            // 如果 stateless 为 true 打开状态，则 每次请求都必须携带 accessToken 请求才行，否则将无法访问
            resources.resourceId(RESOURCE_ID).stateless(true);

            resources.tokenExtractor(customTokenExtractor);
            resources.authenticationEntryPoint(authExceptionEntryPoint)
                    .accessDeniedHandler(customAccessDeniedHandler);
        }


        /**
         * 为oauth2单独创建角色，这些角色只具有访问受限资源的权限，可解决token失效的问题
         * @param http
         * @throws Exception
         */
        @Override
        public void configure(HttpSecurity http) throws Exception {

            /*
            //https://www.cnblogs.com/haoxianrui/p/13719356.html
            http.oauth2ResourceServer().jwt()
                    .jwtAuthenticationConverter(jwtAuthenticationConverter());
            http.oauth2ResourceServer().authenticationEntryPoint(customServerAuthenticationEntryPoint);
            */
            http
                // 获取登录用户的 session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                    // 资源服务器拦截的路径 注意此路径不要和主过滤器冲突
                    .requestMatchers().antMatchers(oAuth2UrlConfig.getUrl()+"**");
            //
            http
                .authorizeRequests()
                    .antMatchers("/api/user/login","/api/user/verifyCode","/api/user/register","/api/user/loginThird","/api/user/weixinLogin" ,
                            "/authentication/form",
                            "/authentication/mobile",
                            "/authentication/openid",
                            "/oauth/authorize", "/oauth/token")
                    .permitAll()
                     // 配置资源服务器已拦截的路径才有效
                    .antMatchers(oAuth2UrlConfig.getUrl()+"**").authenticated();
                    // .access(" #oauth2.hasScope('select') or hasAnyRole('ROLE_超级管理员', 'ROLE_设备管理员')");
            //            //
            http
                .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                    .and().exceptionHandling()
                    .authenticationEntryPoint(new SecurityAuthenticationEntryPoint())
                    .and().exceptionHandling()
                    .accessDeniedHandler(new SecurityAccessDeniedHandler());
        }
    }


    /**
     * 认证服务器。
     */
    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        private  AuthenticationManager authenticationManager;
        @Autowired
        private  DataSource dataSource;
        @Autowired
        @Qualifier("userDetailsServiceImpl")
        private  UserDetailsService userDetailsService;
        @Autowired
        private  ClientDetailsService clientDetailsService;
        @Autowired
        private  AuthorizationCodeServices authorizationCodeServices;
        @Autowired
        private  PasswordEncoder passwordEncoder;
        @Autowired
        private  StringRedisTemplate stringRedisTemplate;

        /**
         * ClientDetails实现
         * 数据库管理client  从数据库（oauth_client_details） 读取客户端数据
         * @return
         */
        @Bean
        public ClientDetailsService clientDetails() {
            return new JdbcClientDetailsService(dataSource);
        }

        /**
         * 数据库管理access_token和refresh_token
         * @return
         */
        @Bean
        public TokenStore tokenStore() {
            //new JwtTokenStore(jwtAccessTokenConverter());
            //new JdbcTokenStore(dataSource);
            //return new CustomJwtJdbcTokenStore(jwtAccessTokenConverter(), dataSource);
            //return new JdbcTokenStore(dataSource);
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        /**
         * 加入对授权码模式的支持
         *  数据库管理授权码
         *  授权码默认生成类
         *  <code>RandomValueAuthorizationCodeServices</code>
         *  <code>RandomValueStringGenerator</code>
         * @param dataSource
         * @return
         */
        @Bean
        public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
            return new JdbcAuthorizationCodeServices(dataSource);
        }


        // @Bean
        // public TokenStore redisTokenStore() {
        //     return new RedisTokenStore(redisConnectionFactory);
        // }

        /**
         * 数据库管理client，从dataSource配置的数据源读取客户端数据
         * 这里指定从数据库（oauth_client_details）
         *
         * 读取客户端数据
         * @param clients
         * @throws Exception
         */
        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            // 1. 数据库的方式
            clients.withClientDetails(clientDetails());
            // 2. 內存的方式
            /*clients.inMemory()
                    .withClient("c1")
                    .secret(new BCryptPasswordEncoder().encode("secret"))
                    //资源列表
                    .resourceIds("res1")
                    //授权类型
                    .authorizedGrantTypes("authorization_code","password","client_credentials","implicit","refresh_token")
                    //允许的授权范围，all是自定义的字符串
                    .scopes("all")
                    //false代表跳转到授权页面
                    .autoApprove(false)
                    //验证回调地址
                    .redirectUris("http://www.baidu.com");*/
        }


        /**
         *  JWT编码的令牌值和OAuth身份验证信息之间进行转换
         * @return
         */
        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter(){
            JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
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


        /**
         * 声明授权和token的端点以及token的服务的一些配置信息，
         * 比如采用什么存储方式、token的有效期等
         * @param endpoints
         */
        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) {

            //令牌增强器。将增强的token设置到增强链中
            TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
            tokenEnhancers.add(customTokenEnhancer());
            tokenEnhancers.add(jwtAccessTokenConverter());
            tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);

            // 获取原有默认授权模式(授权码模式、密码模式、客户端模式、简化模式)的授权者
            List<TokenGranter> granterList = new ArrayList<>(Arrays.asList(endpoints.getTokenGranter()));
            // 添加验证码授权模式授权者
            granterList.add(new CaptchaTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                    endpoints.getOAuth2RequestFactory(), authenticationManager, stringRedisTemplate
            ));
            // 添加手机短信验证码授权模式的授权者
            granterList.add(new SmsCodeTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                    endpoints.getOAuth2RequestFactory(), authenticationManager
            ));

            CompositeTokenGranter compositeTokenGranter = new CompositeTokenGranter(granterList);

            TokenStore tokenStore = tokenStore();

            endpoints
                    .authenticationManager(authenticationManager)     //授权码模式需要
                    .accessTokenConverter(jwtAccessTokenConverter())
                    .tokenEnhancer(tokenEnhancerChain)              //配置token增强
                    .tokenGranter(compositeTokenGranter)            //自定义授权方式
                    /** refresh token有两种使用方式：重复使用(true)、非重复使用(false)，默认为true
                     *  1 重复使用：access token过期刷新时， refresh token过期时间未改变，仍以初次生成的时间为准
                     *  2 非重复使用：access token过期刷新时， refresh token过期时间延续，在refresh token有效期内刷新便永不失效达到无需再次登录的目的
                     */
                    .reuseRefreshTokens(true)
                    .tokenServices(tokenServices(endpoints))
                    .tokenStore(tokenStore)                         //token
                    .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)  //  oauth/token 请求支持GET POST

                    .authorizationCodeServices(authorizationCodeServices) //授权code
                    .userDetailsService(userDetailsService)             //用户信息查询服务
                    .setClientDetailsService(clientDetailsService);     //客户端信息


            // 数据库管理授权信息
            ApprovalStore approvalStore = new JdbcApprovalStore(dataSource);
            endpoints.approvalStore(approvalStore);

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
            //允许表单认证
            oauthServer.allowFormAuthenticationForClients();
            oauthServer.passwordEncoder(passwordEncoder);
            // 对于CheckEndpoint控制器[框架自带的校验]的/oauth/check端点允许所有客户端发送器请求而不会被Spring-security拦截
            oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
            oauthServer.realm("oauth2");
        }

    }
}
