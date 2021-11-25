package com.xiushang.security.authentication.mobile;

import com.xiushang.common.components.SmsService;
import com.xiushang.common.user.vo.LoginSmsVo;
import com.xiushang.framework.utils.WebUtil;
import com.xiushang.framework.web.BodyReaderHttpServletRequestWrapper;
import com.xiushang.security.exception.ValidateException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


@Slf4j
@Data
public class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean {

    /**
     * 验证码校验失败处理器
     */
    private AuthenticationFailureHandler authenticationFailureHandler;

    private SmsService smsService;

    private Set<String> urls = new HashSet<>();

    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();


    /**
     * 初始化要拦截的url配置信息
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
       /* String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(
                securityProperties.getCode().getSms().getUrl(), ",");
        for (String configUrl : configUrls) {
            urls.add(configUrl);
        }*/
        urls.add("/authentication/mobile");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        //解决WebUtil.getJsonBody 只能使用一次问题
        HttpServletRequest requestWrapper = null;
        if( request instanceof BodyReaderHttpServletRequestWrapper) {
            requestWrapper = request;
        }else{
            requestWrapper = new BodyReaderHttpServletRequestWrapper( request);
        }

        boolean action = false;
        for (String url : urls) {
            if (pathMatcher.match(url, request.getRequestURI())) {
                action = true;
            }
        }

        if (action) {
            LoginSmsVo loginSmsVo = WebUtil.getJsonBody(requestWrapper, LoginSmsVo.class);
            if(StringUtils.isBlank(loginSmsVo.getMobile())){
                authenticationFailureHandler.onAuthenticationFailure(request, response, new ValidateException("手机号不能为空！"));
                return;
            }
            if(StringUtils.isBlank(loginSmsVo.getVerifyCode())){
                authenticationFailureHandler.onAuthenticationFailure(request, response, new ValidateException("验证码不能为空！"));
                return;
            }
            if(!smsService.validateCode(loginSmsVo.getMobile(),loginSmsVo.getVerifyCode())){
                authenticationFailureHandler.onAuthenticationFailure(request, response, new ValidateException("短信验证码不正确或已过期！"));
                return;
            }
        }
        chain.doFilter(requestWrapper, response);
    }

}
