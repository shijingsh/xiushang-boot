package com.xiushang.security.config;


import com.xiushang.framework.log.SecurityConstants;
import com.xiushang.security.granter.SmsCodeTokenGranter;
import com.xiushang.security.hadler.AuthExceptionEntryPoint;
import com.xiushang.security.hadler.CustomTokenExtractor;
import com.xiushang.security.hadler.SecurityAccessDeniedHandler;
import com.xiushang.security.hadler.SecurityAuthenticationEntryPoint;
import com.xiushang.security.token.CustomTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

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
                            "/authentication/openid")
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
        // @Autowired
        // private RedisConnectionFactory redisConnectionFactory;

        @Autowired
        private TokenGranter tokenGranter;

        private AuthorizationServerTokenServices tokenServices;

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
        // @Bean
        // public ApprovalStore approvalStore() {
        //     TokenApprovalStore store = new TokenApprovalStore();
        //     store.setTokenStore(tokenStore());
        //     return store;
        // }

        // @Bean
        // public DefaultTokenServices defaultTokenServices(){
        //     DefaultTokenServices tokenServices = new DefaultTokenServices();
        //     tokenServices.setTokenStore(redisTokenStore());
        //     tokenServices.setSupportRefreshToken(true);
        //     tokenServices.setClientDetailsService(clientDetails());
        //     // token有效期自定义设置，默认12小时
        //     tokenServices.setAccessTokenValiditySeconds(60 * 3);
        //     //默认30天，这里修改
        //     tokenServices.setRefreshTokenValiditySeconds(60 * 60);
        //     return tokenServices;
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

            //TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
            //enhancerChain.setTokenEnhancers(Arrays.asList(customTokenEnhancer(), jwtAccessTokenConverter()));

            TokenStore tokenStore = tokenStore();
            endpoints.tokenStore(tokenStore)
                    //.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                    .authenticationManager(authenticationManager) //授权码模式需要
                    .authorizationCodeServices(authorizationCodeServices) //密码模式需要
                    .userDetailsService(userDetailsService)             //用户信息查询服务
                    .setClientDetailsService(clientDetailsService);     //


            // 数据库管理授权码
            //endpoints.authorizationCodeServices(new JdbcAuthorizationCodeServices(dataSource));

            // 数据库管理授权信息
            ApprovalStore approvalStore = new JdbcApprovalStore(dataSource);
            endpoints.approvalStore(approvalStore);

            //令牌库 生成令牌控制
            DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setTokenStore(tokenStore);
            tokenServices.setSupportRefreshToken(true);
            tokenServices.setClientDetailsService(new JdbcClientDetailsService(dataSource));
            // tokenServices.setAccessTokenValiditySeconds(60 * 3);   //token有效期自定义设置，默认12小时
            // tokenServices.setRefreshTokenValiditySeconds(60 * 60);  //默认30天，这里修改

            endpoints.tokenServices(tokenServices);

            endpoints.tokenGranter(tokenGranter);
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


        //-------------------自定义 TokenGranter-------------------

        @Bean
        public TokenGranter tokenGranter(){
            if(null == tokenGranter){
                tokenGranter = new TokenGranter() {
                    private CompositeTokenGranter delegate;

                    @Override
                    public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
                        if(delegate == null){
                            delegate = new CompositeTokenGranter(getDefaultTokenGranters());
                        }
                        return delegate.grant(grantType,tokenRequest);
                    }
                };
            }
            return tokenGranter;
        }


        private AuthorizationServerTokenServices tokenServices() {
            if (tokenServices != null) {
                return tokenServices;
            }
            this.tokenServices = createDefaultTokenServices();
            return tokenServices;
        }


        private AuthorizationServerTokenServices createDefaultTokenServices() {
            TokenStore tokenStore = tokenStore();

            DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setTokenStore(tokenStore);
            tokenServices.setSupportRefreshToken(true);
            tokenServices.setReuseRefreshToken(true);
            tokenServices.setClientDetailsService(clientDetailsService);
            tokenServices.setTokenEnhancer(customTokenEnhancer());

            //添加预身份验证
            if (userDetailsService != null) {
                PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
                provider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper<>(userDetailsService));
                tokenServices.setAuthenticationManager(new ProviderManager(Arrays.asList(provider)));
            }
            return tokenServices;
        }


        /**
         * OAuth2RequestFactory的默认实现，它初始化参数映射中的字段，
         * 验证授权类型(grant_type)和范围(scope)，并使用客户端的默认值填充范围(scope)（如果缺少这些值）。
         */
        private OAuth2RequestFactory requestFactory() {
            return new DefaultOAuth2RequestFactory(clientDetailsService);
        }


        private List<TokenGranter> getDefaultTokenGranters() {
            AuthorizationServerTokenServices tokenServices = tokenServices();
            OAuth2RequestFactory requestFactory = requestFactory();

            List<TokenGranter> tokenGranters = new ArrayList();
            //授权码模式
            tokenGranters.add(new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, clientDetailsService, requestFactory));
            //refresh模式
            tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetailsService, requestFactory));
            //简化模式
            ImplicitTokenGranter implicit = new ImplicitTokenGranter(tokenServices, clientDetailsService, requestFactory);
            tokenGranters.add(implicit);
            //客户端模式
            tokenGranters.add(new ClientCredentialsTokenGranter(tokenServices, clientDetailsService, requestFactory));

            if (authenticationManager != null) {
                //密码模式
                tokenGranters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices, clientDetailsService, requestFactory));
                //短信验证码模式
                tokenGranters.add(new SmsCodeTokenGranter(authenticationManager, tokenServices, clientDetailsService, requestFactory));
            }

            return tokenGranters;
        }
    }
}
