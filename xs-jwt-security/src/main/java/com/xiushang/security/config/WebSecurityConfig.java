package com.xiushang.security.config;

import com.xiushang.common.components.SmsService;
import com.xiushang.config.JWTIgnoreUrlsConfig;
import com.xiushang.jpa.repository.OauthClientDetailsDao;
import com.xiushang.jpa.repository.UserSocialDao;
import com.xiushang.security.authentication.client.ClientAuthenticationProvider;
import com.xiushang.security.authentication.mobile.SmsCodeAuthenticationProvider;
import com.xiushang.security.authentication.social.SocialAuthenticationProvider;
import com.xiushang.security.authentication.username.UserNameAuthenticationProvider;
import com.xiushang.security.authentication.wechat.WechatAuthenticationProvider;
import com.xiushang.security.filter.UrlFilterInvocationSecurityMetadataSource;
import com.xiushang.security.hadler.SecurityAccessDeniedHandler;
import com.xiushang.security.hadler.SecurityAuthenticationEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import java.util.List;

@Configuration
@EnableWebSecurity
@Slf4j
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("urlFilterInvocationSecurityMetadataSource")
    UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;

    @Autowired
    @Qualifier("urlAccessDecisionManager")
    AccessDecisionManager urlAccessDecisionManager;


    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private JWTIgnoreUrlsConfig ignoreUrlsConfig;
    @Autowired
    private UserSocialDao userSocialDao;
    @Autowired
    private OauthClientDetailsDao oauthClientDetailsDao;
    /**
     * 访问静态资源
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
    "/css/**",
                "/js/**",
                "/images/**",
                "/fonts/**",
                "/favicon.ico",
                "/static/**",
                "/resources/**",
                "/error",
                "/*.html",
                "/webjars/**",
                "/swagger-resources/**");
    }

    /**
     * 添加自定义授权者
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(userNameAuthenticationProvider())
                .authenticationProvider(smsCodeAuthenticationProvider())
                .authenticationProvider(socialAuthenticationProvider())
                .authenticationProvider(wechatAuthenticationProvider())
                .authenticationProvider(clientAuthenticationProvider())
        ;
    }


    /**
     * 用户名密码认证授权提供者
     *
     * @return
     */
    @Bean
    public UserNameAuthenticationProvider userNameAuthenticationProvider() {
        UserNameAuthenticationProvider provider = new UserNameAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);

        return provider;
    }

    /**
     * 社交账号认证授权提供者
     *
     * @return
     */
    @Bean
    public SocialAuthenticationProvider socialAuthenticationProvider() {
        SocialAuthenticationProvider provider = new SocialAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setUserSocialDao(userSocialDao);
        return provider;
    }


    /**
     * 客户端认证授权提供者
     *
     * @return
     */
    @Bean
    public ClientAuthenticationProvider clientAuthenticationProvider() {
        ClientAuthenticationProvider provider = new ClientAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setOauthClientDetailsDao(oauthClientDetailsDao);
        return provider;
    }

    /**
     * 微信code认证授权提供者
     *
     * @return
     */
    @Bean
    public WechatAuthenticationProvider wechatAuthenticationProvider() {
        WechatAuthenticationProvider provider = new WechatAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);

        return provider;
    }

    /**
     * 手机验证码认证授权提供者
     *
     * @return
     */
    @Bean
    public SmsCodeAuthenticationProvider smsCodeAuthenticationProvider() {
        SmsCodeAuthenticationProvider provider = new SmsCodeAuthenticationProvider();
        provider.setMyUserDetailsService(userDetailsService);
        provider.setSmsService(smsService);
        return provider;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        List<String> list = ignoreUrlsConfig.getUrls();
        String[] AUTH_WHITELIST = list.toArray(new String[list.size()]);

        http
            .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers("/*.html","/*.svg","/*.png","/*.jpg","/*.js","/*.css").permitAll()
                .antMatchers("/v2/api-docs",
                        "/authentication/require",
                        "/oauthLogin",
                        "/oauthGrant",
                        "/authentication/form",
                        "/authentication/mobile",
                        "/authentication/openid",
                        "/authentication/logout").permitAll()
                .anyRequest()
                .authenticated()
                .withObjectPostProcessor(urlObjectPostProcessor())
            .and()
                .formLogin()
                .loginPage("/authentication/require")
                .loginProcessingUrl("/authentication/form")
                //.usernameParameter("username")
                //.passwordParameter("password")
                .permitAll()
                .failureHandler(new com.xiushang.security.hadler.SecurityLoginFailureHandler())
                //.successHandler(new com.xiushang.security.hadler.SecurityLoginSuccessHandler())
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(new SecurityAuthenticationEntryPoint())
                .accessDeniedHandler(new SecurityAccessDeniedHandler())
            .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .logoutUrl("/logout")
                //.logoutSuccessHandler(new com.xiushang.security.hadler.SecurityLogoutSuccessHandler())
                .permitAll()
            .and()
                .csrf()
                .disable();

        http
                .sessionManagement()
                // 无效session跳转
                .invalidSessionUrl("/authentication/require")
                // 同账号最大允许登录数
                .maximumSessions(1)
                // session过期跳转
                .expiredUrl("/authentication/require")
                .sessionRegistry(sessionRegistry());
    }

    /**
     * 解决session失效后 sessionRegistry中session没有同步失效的问题
     * @return
     */
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    public ObjectPostProcessor urlObjectPostProcessor() {
        return new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                o.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource);
                o.setAccessDecisionManager(urlAccessDecisionManager);
                return o;
            }
        };
    }

    /**
     * 设置加密方式
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
