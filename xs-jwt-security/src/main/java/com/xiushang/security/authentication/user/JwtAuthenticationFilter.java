package com.xiushang.security.authentication.user;

import com.xiushang.common.user.vo.LoginSmsVo;
import com.xiushang.framework.utils.WebUtil;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 请求中，参数为mobile
     */
    private static final String MOBILE_KEY = "mobile";
    private String mobileParameter = MOBILE_KEY;
    /**
     * 是否只处理post请求
     */
    private boolean postOnly = true;

    public JwtAuthenticationFilter() {
        //要拦截的请求
        super(new AntPathRequestMatcher("/api/**", null));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {

            LoginSmsVo loginSmsVo = WebUtil.getJsonBody(request, LoginSmsVo.class);

            String mobile = loginSmsVo.getMobile();
            if (mobile == null) {
                mobile = "";
            }

            mobile = mobile.trim();
            //把手机号传进SmsCodeAuthenticationToken
            JwtAuthenticationToken authRequest = new JwtAuthenticationToken(mobile);
            this.setDetails(request, authRequest);
            //调用AuthenticationManager
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    /**
     * 获取手机号
     *
     * @param request request
     * @return String
     */
    private String obtainMobile(HttpServletRequest request) {
        return request.getParameter(this.mobileParameter);
    }

    private void setDetails(HttpServletRequest request, JwtAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setMobileParameter(String mobileParameter) {
        Assert.hasText(mobileParameter, "Mobile parameter must not be empty or null");
        this.mobileParameter = mobileParameter;
    }


    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getUsernameParameter() {
        return this.mobileParameter;
    }
}
