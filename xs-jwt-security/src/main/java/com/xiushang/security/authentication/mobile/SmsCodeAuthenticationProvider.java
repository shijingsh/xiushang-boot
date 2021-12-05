package com.xiushang.security.authentication.mobile;

import com.xiushang.common.components.SmsService;
import com.xiushang.security.SecurityUser;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashSet;

@Data
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService myUserDetailsService;

    private SmsService smsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
        String mobile = (String) authenticationToken.getPrincipal();
        String code = (String) authenticationToken.getCredentials();

        if(StringUtils.isBlank(mobile)){
            throw new InternalAuthenticationServiceException("验证码不正确");
        }
        if(StringUtils.isBlank(code)){
            throw new InternalAuthenticationServiceException("验证码不能为空");
        }
        if(!smsService.validateCode(mobile,code)){
            throw new InternalAuthenticationServiceException("验证码不正确");
        }

        //校验手机号
        SecurityUser securityUser = (SecurityUser)  myUserDetailsService.loadUserByUsername(mobile);
        if (securityUser == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        SmsCodeAuthenticationToken result = new SmsCodeAuthenticationToken(securityUser, authentication.getCredentials(), securityUser.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
