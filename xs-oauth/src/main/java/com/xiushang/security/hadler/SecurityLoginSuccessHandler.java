package com.xiushang.security.hadler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiushang.common.utils.JsonUtils;
import com.xiushang.framework.log.CommonResult;
import com.xiushang.framework.sys.PropertyConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class SecurityLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private ClientDetailsService clientDetailsService;

	@Autowired
	private AuthorizationServerTokenServices authorizationServerTokenServices;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		// 登录成功
		log.info("登录成功");

		String type = request.getHeader("Accept");
		if(!type.contains("text/html")){

			String clientId = PropertyConfigurer.getConfig("oauth.client.prex");

			ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
			if (null == clientDetails) {
				throw new UnapprovedClientAuthenticationException("clientId不存在" + clientId);
			}

			TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");

			OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

			OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

			OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

			response.setCharacterEncoding("utf-8");
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);

			CommonResult<OAuth2AccessToken> commonResult = CommonResult.success(token);
			String resBody = JsonUtils.toJsonStr(commonResult);

			PrintWriter printWriter = response.getWriter();
			printWriter.print(resBody);
			printWriter.flush();
			printWriter.close();
		}
	}
}
