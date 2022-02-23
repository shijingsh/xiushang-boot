package com.xiushang.security.config;

import com.xiushang.common.components.SmsService;
import com.xiushang.common.user.service.SystemParamService;
import com.xiushang.common.user.service.UserService;
import com.xiushang.config.JWTIgnoreUrlsConfig;
import com.xiushang.framework.sys.PropertyConfigurer;
import com.xiushang.jpa.repository.OauthClientDetailsDao;
import com.xiushang.jpa.repository.ShopDao;
import com.xiushang.jpa.repository.UserSocialDao;
import com.xiushang.security.authentication.client.ClientAuthenticationProvider;
import com.xiushang.security.authentication.mobile.SmsCodeAuthenticationProvider;
import com.xiushang.security.authentication.social.SocialAuthenticationProvider;
import com.xiushang.security.authentication.username.UserNameAuthenticationProvider;
import com.xiushang.security.authentication.wechat.WechatAuthenticationProvider;
import com.xiushang.security.filter.CustomUsernamePasswordAuthenticationFilter;
import com.xiushang.security.filter.UrlFilterInvocationSecurityMetadataSource;
import com.xiushang.security.hadler.SecurityAccessDeniedHandler;
import com.xiushang.security.hadler.SecurityAuthenticationEntryPoint;
import com.xiushang.security.hadler.SecurityLoginSuccessHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import java.util.ArrayList;
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
    private UserService userService;

    @Autowired
    private JWTIgnoreUrlsConfig ignoreUrlsConfig;
    @Autowired
    private UserSocialDao userSocialDao;
    @Autowired
    private OauthClientDetailsDao oauthClientDetailsDao;
    @Autowired
    private SystemParamService systemParamService;
    @Autowired
    private ShopDao shopDao;
    @Autowired
    private SecurityLoginSuccessHandler loginSuccessHandler;

    public static final String loginProcessUrl = "/authentication/login";

    public static final String loginPageUrl = "/oauthLogin";

    public static final String[] defaultWhiteList = {"/","/v2/api-docs","/*.html","/*.svg","/*.png","/*.jpg","/*.bmp","/*.js","/*.css",
            "/oauthLogin","/oauthGrant",
            "/index**","/login**",  "/error**",
            "/register","/verifyCode","/captcha",
            "/authentication/**"
    };

    public static final String[] staticWhiteList = {"/css/**",
            "/js/**",
            "/images/**",
            "/fonts/**",
            "/favicon.ico",
            "/static/**",
            "/resources/**",
            "/error",
            "/*.html",
            "/v2/api-docs",
            "/webjars/**",
            "/swagger-resources/**"
    };
    /**
     * 访问静态资源
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(staticWhiteList);
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
        provider.setOauthClientDetailsDao(oauthClientDetailsDao);

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
        provider.setOauthClientDetailsDao(oauthClientDetailsDao);
        provider.setUserSocialDao(userSocialDao);
        provider.setUserService(userService);
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
        provider.setOauthClientDetailsDao(oauthClientDetailsDao);
        provider.setUserService(userService);
        provider.setShopDao(shopDao);
        provider.setSystemParamService(systemParamService);

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
        provider.setUserDetailsService(userDetailsService);
        provider.setOauthClientDetailsDao(oauthClientDetailsDao);
        provider.setSmsService(smsService);
        return provider;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //免登录URL
        String[] authWhiteList = getWhiteList();

        //https 登录页配置
        String loginPage = getLoginPage();

        http
            .authorizeRequests()
                .antMatchers(authWhiteList).permitAll()
                .anyRequest()
                .authenticated()
                //.withObjectPostProcessor(urlObjectPostProcessor())   /* 动态url权限 */
                //.accessDecisionManager(accessDecisionManager())         /* url决策 */

            .and()
                .formLogin()
                .loginPage(loginPage)
                .loginProcessingUrl(loginProcessUrl)
                //.usernameParameter("username")
                //.passwordParameter("password")
                .permitAll()
                .failureHandler(new com.xiushang.security.hadler.SecurityLoginFailureHandler())      /* 登录失败后的处理 */
                .successHandler(loginSuccessHandler)      /* 登录成功后的处理 */
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(new SecurityAuthenticationEntryPoint())       /* 登录过期/未登录 处理 */
                .accessDeniedHandler(new SecurityAccessDeniedHandler())                 /* 权限不足(没有赋予角色) 处理 */
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
                .invalidSessionUrl(loginPage)
                // 同账号最大允许登录数
                .maximumSessions(1)
                // session过期跳转
                .expiredUrl(loginPage)
                .sessionRegistry(sessionRegistry());

        // 用重写的Filter替换掉原有的UsernamePasswordAuthenticationFilter，支持json格式
        http.addFilterAt(customAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class);
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

    @Bean
    public CustomUsernamePasswordAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomUsernamePasswordAuthenticationFilter filter = new CustomUsernamePasswordAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(loginSuccessHandler);
        filter.setAuthenticationFailureHandler(new com.xiushang.security.hadler.SecurityLoginFailureHandler());
        filter.setFilterProcessesUrl(loginProcessUrl);
        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    private String getLoginPage(){
        //https 登录页配置
        String httpsRoot = PropertyConfigurer.getConfig("oauth.client.root");
        String loginPageProperty = PropertyConfigurer.getConfig("oauth.client.login");
        String loginPage = loginPageUrl;
        if(StringUtils.isNotBlank(loginPageProperty)){
            loginPage = loginPageProperty;
        }
        if(StringUtils.isNotBlank(httpsRoot) && !loginPage.startsWith("https")){
            //解决https下重定向的次数过多问题。
            loginPage = httpsRoot +loginPage;
        }

        return loginPage;
    }

    private String[] getWhiteList(){
        List<String> list = ignoreUrlsConfig.getUrls();
        if(list==null){
            list = new ArrayList<>();
        }

        for (String url:defaultWhiteList){
            list.add(url);
        }
        String[] authWhiteList = list.toArray(new String[list.size()]);

        return  authWhiteList;
    }
}
