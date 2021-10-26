package com.xiushang.handler;

import com.xiushang.common.utils.JsonUtils;
import com.xiushang.framework.log.CommonResult;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Auther: liukefu
 * @Description: 自定义认证拦截器
 */
public class Http401AuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final String headerValue;

    public Http401AuthenticationEntryPoint(String headerValue) {
        this.headerValue = headerValue;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //response.setHeader("Authorization", this.headerValue);
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        CommonResult<String> commonResult = CommonResult.error(401,"未登录或登录已过期，请重新登录。");
        String resBody = JsonUtils.toJsonStr(commonResult);

        PrintWriter printWriter = response.getWriter();
        printWriter.print(resBody);
        printWriter.flush();
        printWriter.close();
    }

}
