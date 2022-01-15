package com.xiushang.security.authentication.client;

import com.xiushang.entity.oauth.OauthClientDetailsEntity;
import com.xiushang.security.SecurityRole;
import com.xiushang.security.SecurityRoleVo;
import com.xiushang.security.SecurityUser;
import com.xiushang.security.authentication.TenantProvider;
import com.xiushang.service.impl.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.ArrayList;
import java.util.List;


public class ClientAuthenticationProvider extends TenantProvider implements AuthenticationProvider {


    @Override
    public boolean supports(Class<?> authentication) {
        //支持SocialAuthenticationToken来验证
        return ClientAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //这个authentication就是SocialAuthenticationToken
        ClientAuthenticationToken authenticationToken = (ClientAuthenticationToken) authentication;

        //校验clientId
        String clientId = (String) authenticationToken.getPrincipal();

        OauthClientDetailsEntity detailsEntity = getOauthClientDetailsDao().findByClientId(clientId);

        if(detailsEntity == null){
            throw new InternalAuthenticationServiceException("无法获取用户账号信息");
        }

        String userId = detailsEntity.getUserId();
        SecurityUser securityUser = (SecurityUser)((UserDetailsServiceImpl)getUserDetailsService()).loadUserByUserId(userId);

        if(securityUser == null ){
            throw new InternalAuthenticationServiceException("无法获取用户账号信息");
        }

        //客户端的授权者即是租户
        securityUser.setTenantId(userId);

        //附加权限
        //List<SecurityRoleVo> list = new ArrayList<>();
        //list.add(new SecurityRoleVo(SecurityRole.ROLE_CLIENT));
        //这时候已经认证成功了
        ClientAuthenticationToken authenticationResult = new ClientAuthenticationToken(authenticationToken.getClientId(),securityUser, securityUser.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

}
