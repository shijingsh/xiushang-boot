package com.xiushang.security.authentication.client;

import com.xiushang.entity.OauthClientDetailsEntity;
import com.xiushang.entity.UserSocialEntity;
import com.xiushang.jpa.repository.OauthClientDetailsDao;
import com.xiushang.jpa.repository.UserSocialDao;
import com.xiushang.security.SecurityUser;
import com.xiushang.service.impl.UserDetailsServiceImpl;
import com.xiushang.util.SocialTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;


public class ClientAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    private OauthClientDetailsDao oauthClientDetailsDao;

    @Override
    public boolean supports(Class<?> authentication) {
        //支持SocialAuthenticationToken来验证
        return ClientAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //这个authentication就是SocialAuthenticationToken
        ClientAuthenticationToken authenticationToken = (ClientAuthenticationToken) authentication;

        //校验手机号
        String clientId = (String) authenticationToken.getPrincipal();

        OauthClientDetailsEntity detailsEntity = oauthClientDetailsDao.findByClientId(clientId);

        if(detailsEntity == null){
            throw new InternalAuthenticationServiceException("无法获取用户账号信息");
        }

        String userId = detailsEntity.getUserId();
        SecurityUser securityUser = (SecurityUser)((UserDetailsServiceImpl)userDetailsService).loadUserByUserId(userId);

        if(securityUser == null ){
            throw new InternalAuthenticationServiceException("无法获取用户账号信息");
        }


        //这时候已经认证成功了
        ClientAuthenticationToken authenticationResult = new ClientAuthenticationToken(securityUser, securityUser.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public OauthClientDetailsDao getOauthClientDetailsDao() {
        return oauthClientDetailsDao;
    }

    public void setOauthClientDetailsDao(OauthClientDetailsDao oauthClientDetailsDao) {
        this.oauthClientDetailsDao = oauthClientDetailsDao;
    }
}
