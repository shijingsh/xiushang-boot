package com.xiushang.security.authentication.mobile;

import com.xiushang.common.components.SmsService;
import com.xiushang.security.SecurityRole;
import com.xiushang.security.SecurityRoleVo;
import com.xiushang.security.SecurityUser;
import com.xiushang.security.authentication.TenantProvider;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.ArrayList;
import java.util.List;

@Data
public class SmsCodeAuthenticationProvider extends TenantProvider implements AuthenticationProvider {

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
        SecurityUser securityUser = (SecurityUser)  super.getUserDetailsService().loadUserByUsername(mobile);
        if (securityUser == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        //设置租户信息
        super.settingTenantId(securityUser, authenticationToken);

        //附加权限
        List<SecurityRoleVo> list = new ArrayList<>();
        list.add(new SecurityRoleVo(SecurityRole.ROLE_CLIENT));
        list.add(new SecurityRoleVo(SecurityRole.ROLE_USER));

        SmsCodeAuthenticationToken result = new SmsCodeAuthenticationToken(authenticationToken.getClientId(),securityUser, authentication.getCredentials(), securityUser.getAuthorities(list));
        result.setDetails(authentication.getDetails());
        return result;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
