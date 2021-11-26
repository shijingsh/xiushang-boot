package com.xiushang.config;


import com.xiushang.security.hadler.AuthExceptionEntryPoint;
import com.xiushang.security.hadler.CustomTokenExtractor;
import com.xiushang.security.hadler.SecurityAccessDeniedHandler;
import com.xiushang.security.hadler.SecurityAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

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
            return new JdbcTokenStore(dataSource);
        }

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
         * 声明授权和token的端点以及token的服务的一些配置信息，
         * 比如采用什么存储方式、token的有效期等
         * @param endpoints
         */
        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) {

            endpoints.tokenStore(tokenStore())
                    .authenticationManager(authenticationManager) //授权码模式需要
                    .authorizationCodeServices(authorizationCodeServices) //密码模式需要
                    .userDetailsService(userDetailsService)
                    .setClientDetailsService(clientDetailsService);
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
