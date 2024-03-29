package com.xiushang.security.token;

import com.xiushang.framework.log.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Slf4j
@Component
public class CustomTokenExtractor implements TokenExtractor {

    @Override
    public Authentication extract(HttpServletRequest request) {
        String tokenValue = this.extractToken(request);
        if (tokenValue != null) {
            return new PreAuthenticatedAuthenticationToken(tokenValue, "");
        } else {
            return null;
        }
    }

    public String extractToken(HttpServletRequest request) {
        String token = this.extractHeaderToken(request);
        if (token == null) {
            log.info("Token not found in headers. Trying request parameters.");
            token = request.getParameter("access_token");
            if (token == null) {
                log.info("Token not found in request parameters.  Trying request cookies.");

                token = extractCookieToken(request);
                if (token == null) {
                    log.info("Token not found in cookies.  Not an OAuth2 request.");
                } else {
                    request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_TYPE, SecurityConstants.TOKEN_PREFIX);
                }
            }
        }
        return token;
    }

    private String extractHeaderToken(HttpServletRequest request) {
        Enumeration headers = request.getHeaders(SecurityConstants.AUTH_HEADER_STRING);

        String value;
        do {
            if (!headers.hasMoreElements()) {
                return null;
            }

            value = (String) headers.nextElement();
        } while (!value.toLowerCase().startsWith(SecurityConstants.TOKEN_PREFIX.toLowerCase()));

        String authHeaderValue = value.substring(SecurityConstants.TOKEN_PREFIX.length()).trim();
        request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_TYPE, value.substring(0, SecurityConstants.TOKEN_PREFIX.length()).trim());
        int commaIndex = authHeaderValue.indexOf(44);
        if (commaIndex > 0) {
            authHeaderValue = authHeaderValue.substring(0, commaIndex);
        }
        if (authHeaderValue.equals("")) {
            return null;
        }
        return authHeaderValue;
    }

    private String extractCookieToken(HttpServletRequest request) {

        String cookieToken = null;
        Cookie[] cookies = request.getCookies();//根据请求数据，找到cookie数组

        if (null != cookies && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (null != cookie.getName() && cookie.getName().trim().equalsIgnoreCase("access_token")) {
                    cookieToken = cookie.getValue().trim();
                    break;
                }
            }
        }
        return cookieToken;
    }
}
