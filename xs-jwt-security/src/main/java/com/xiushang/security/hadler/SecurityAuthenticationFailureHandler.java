package com.xiushang.security.hadler;

import com.xiushang.common.utils.JsonUtils;
import com.xiushang.framework.log.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component("securityAuthenticationFailureHandler")
@Slf4j
public class SecurityAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request , HttpServletResponse response , AuthenticationException exception) throws IOException {
		// 记录登录失败的日志
		this.saveLog(request, exception);
		log.info("登录失败: "+ exception.getMessage());

		int code = 1;
		String message = exception.getMessage();

		response.setCharacterEncoding("utf-8");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		CommonResult<String> commonResult = CommonResult.error(code,message);
		String resBody = JsonUtils.toJsonStr(commonResult);

		PrintWriter printWriter = response.getWriter();
		printWriter.print(resBody);
		printWriter.flush();
		printWriter.close();
	}

	/**
	 * 记录登录失败的日志
	 * @param request
	 */
	private void saveLog(HttpServletRequest request, AuthenticationException exception) {

	}
}
