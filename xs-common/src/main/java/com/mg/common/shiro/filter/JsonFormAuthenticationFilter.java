package com.mg.common.shiro.filter;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Created by liukefu on 2016/1/11.
 */
public class JsonFormAuthenticationFilter extends FormAuthenticationFilter {


    /**
     * 重写登录地址
     */
    protected void redirectToLogin(ServletRequest request,
                                   ServletResponse response) throws IOException {
        String loginUrl = "/login.html";
        WebUtils.issueRedirect(request, response, loginUrl);
    }
}