package com.xiushang.security.hadler;

import com.xiushang.common.utils.JsonUtils;
import com.xiushang.framework.log.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
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

/**
 * 用户未登录时的处理
 * @author lirong
 * @date 2019-8-8 17:37:27
 */
@Slf4j
@Component
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {

	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		//response.setHeader("Authorization", this.headerValue);
		//response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());

		Throwable cause = authException.getCause();
		if(cause instanceof InvalidTokenException) {
			log.info("InvalidTokenException:" + authException.getMessage());
		}else{
			log.info("cause:" + authException.getMessage());
		}

		int code;
		String message;
		if("Full authentication is required to access this resource".equals(authException.getMessage())) {
			code = HttpServletResponse.SC_FORBIDDEN;
			message = "无权访问，未认证或授权访问受保护的资源。";

			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}else{
			code = HttpServletResponse.SC_UNAUTHORIZED;
			message = "未登录或登录已过期，请重新登录。";

			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}

		response.setCharacterEncoding("utf-8");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		CommonResult<String> commonResult = CommonResult.error(code,message);
		String resBody = JsonUtils.toJsonStr(commonResult);

		PrintWriter printWriter = response.getWriter();
		printWriter.print(resBody);
		printWriter.flush();
		printWriter.close();
	}
}
