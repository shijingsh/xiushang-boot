package com.xiushang.security.hadler;


import com.xiushang.common.utils.JsonUtils;
import com.xiushang.framework.log.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class ResourceExceptionEntryPoint implements AuthenticationEntryPoint
{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws ServletException, IOException {

        Throwable cause = authException.getCause();
        int errorCode = 403;
        if(cause instanceof InvalidTokenException) {
            errorCode = 401;
            log.info("InvalidTokenException:" + authException.getMessage());
        }else{
            log.info("cause:" + authException.getMessage());
        }

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        CommonResult<String> commonResult = CommonResult.error(errorCode,authException.getMessage());
        String resBody = JsonUtils.toJsonStr(commonResult);

        PrintWriter printWriter = response.getWriter();
        printWriter.print(resBody);
        printWriter.flush();
        printWriter.close();
    }
}
