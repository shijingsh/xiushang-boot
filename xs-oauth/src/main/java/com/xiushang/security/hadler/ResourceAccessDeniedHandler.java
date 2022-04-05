package com.xiushang.security.hadler;

import com.xiushang.common.utils.JsonUtils;
import com.xiushang.framework.log.CommonResult;
import com.xiushang.framework.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用户访问没有权限资源的处理
 */

@Slf4j
public class ResourceAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
		log.info(request.getRequestURL()+"没有权限");

		String message = accessDeniedException.getMessage();

		response.setCharacterEncoding("utf-8");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		int errorCode = 1;
		if(UserHolder.getClientAuth()){
			//当仅为客户端授权时，提示用户登录
			errorCode = 401;
		}
		CommonResult<String> commonResult = CommonResult.error(errorCode,message);
		String resBody = JsonUtils.toJsonStr(commonResult);

		PrintWriter printWriter = response.getWriter();
		printWriter.print(resBody);
		printWriter.flush();
		printWriter.close();
	}
}
