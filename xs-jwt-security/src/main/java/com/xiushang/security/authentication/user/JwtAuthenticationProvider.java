package com.xiushang.security.authentication.user;

import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Data
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService myUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //这个authentication就是SmsCodeAuthenticationToken
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;

        //校验手机号
        UserDetails user = myUserDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());
        if (user == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        //这时候已经认证成功了
        JwtAuthenticationToken authenticationResult = new JwtAuthenticationToken(user, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //该SmsCodeAuthenticationProvider智支持SmsCodeAuthenticationToken的token认证
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
