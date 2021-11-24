package com.xiushang.config;

import com.xiushang.filter.JWTAuthenticationFilter;
import com.xiushang.security.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.xiushang.security.authentication.openid.OpenIdAuthenticationConfig;
import com.xiushang.security.hadler.SecurityAccessDeniedHandler;
import com.xiushang.security.hadler.SecurityAuthenticationEntryPoint;
import com.xiushang.security.hadler.SecurityLogoutSuccessHandler;
import com.xiushang.security.manager.UrlAccessDecisionManager;
import com.xiushang.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import com.xiushang.security.provider.SecurityAuthenticationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import java.util.List;

@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private  JWTIgnoreUrlsConfig ignoreUrlsConfig;
    @Autowired
    private  SecurityAuthenticationProvider securityAuthenticationProvider;
    @Autowired
    private  UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;
    @Autowired
    private  UrlAccessDecisionManager urlAccessDecisionManager;

    @Autowired
    private SecurityAccessDeniedHandler securityAccessDeniedHandler;

    @Autowired
    private SecurityAuthenticationEntryPoint securityAuthenticationEntryPoint;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private OpenIdAuthenticationConfig openIdAuthenticationConfig;
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
                "/swagger-ui.html",
                "/swagger-resources/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //用户名、密码登录
        auth.authenticationProvider(securityAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        List<String> list = ignoreUrlsConfig.getUrls();
        String[] AUTH_WHITELIST = list.toArray(new String[list.size()]);

        http.cors().and().csrf().disable()
                /**
                 *  //控制器
                 *   .sessionManagement()
                 *   .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
                 * 们可以通过下列选项控制会话何时创建及如何与SpringSecurity交互
                 *
                 * 机制	描述
                 * always	没有session存在就创建一个
                 * ifRequired	如果有需要就创建一个登陆时(默认)
                 * never	SpringSecurity不会创建session,但是应用其他地方创建来的话,可以使用
                 * stateless	不创建不使用
                 */
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers("/*.html","/*.svg","/*.png","/*.jpg","/*.js","/*.css").permitAll()
                .antMatchers("/v2/api-docs",
                        "/authentication/form",
                        "/authentication/mobile",
                        "/authentication/openid",
                        "/myLogout").permitAll()
                .anyRequest()
                .authenticated().withObjectPostProcessor(urlObjectPostProcessor())

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(securityAuthenticationEntryPoint)
                .and().exceptionHandling()
                .accessDeniedHandler(securityAccessDeniedHandler)

                .and().addFilter(new JWTAuthenticationFilter(authenticationManager()))
                //短信验证码配置
                .apply(smsCodeAuthenticationSecurityConfig)
                //openID登录
                .and().apply(openIdAuthenticationConfig)

                .and().logout() // 默认注销行为为logout，可以通过下面的方式来修改
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")// 设置注销成功后跳转页面，默认是跳转到登录页面;
                .logoutSuccessHandler(new SecurityLogoutSuccessHandler())
                .permitAll()
        ;





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
