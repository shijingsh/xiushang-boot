package com.xiushang.security.hadler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
public class SecurityLogoutSuccessHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request , HttpServletResponse response , Authentication authentication) {

		// 清除登录的session
		request.getSession().invalidate();

		// 记录登出日志
		log.info("退出成功");

	}


}
