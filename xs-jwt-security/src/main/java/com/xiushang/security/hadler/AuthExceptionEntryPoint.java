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
@Component
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint
{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws ServletException, IOException {

        Throwable cause = authException.getCause();
        if(cause instanceof InvalidTokenException) {
            log.info("InvalidTokenException:" + authException.getMessage());
        }else{
            log.info("cause:" + authException.getMessage());
        }

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        CommonResult<String> commonResult = CommonResult.error(403,"无权访问，未认证或授权访问受保护的资源。");
        String resBody = JsonUtils.toJsonStr(commonResult);

        PrintWriter printWriter = response.getWriter();
        printWriter.print(resBody);
        printWriter.flush();
        printWriter.close();
    }
}
