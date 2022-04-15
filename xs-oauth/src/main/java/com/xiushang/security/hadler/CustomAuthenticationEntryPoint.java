package com.xiushang.security.hadler;

import com.xiushang.common.utils.JsonUtils;
import com.xiushang.framework.log.CommonResult;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Throwable cause = authException.getCause();
        int errorCode = 403;
        if(cause instanceof InvalidTokenException) {
            errorCode = 401;
        }

        CommonResult<String> commonResult = CommonResult.error(errorCode,authException.getMessage());
        String resBody = JsonUtils.toJsonStr(commonResult);

        PrintWriter printWriter = response.getWriter();
        printWriter.print(resBody);
        printWriter.flush();
        printWriter.close();
    }
}
