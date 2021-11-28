package com.xiushang.security.authentication.openid;

import com.xiushang.entity.UserSocialEntity;
import com.xiushang.jpa.repository.UserSocialDao;
import com.xiushang.service.impl.UserDetailsServiceImpl;
import com.xiushang.util.SocialTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;


public class OpenIdAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsServiceImpl userDetailsService;

    private UserSocialDao userSocialDao;

    @Override
    public boolean supports(Class<?> authentication) {
        //支持OpenIdAuthenticationToken来验证
        return OpenIdAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //这个authentication就是OpenIdAuthenticationToken
        OpenIdAuthenticationToken authenticationToken = (OpenIdAuthenticationToken) authentication;

        //校验手机号
        String providerUserId = (String) authenticationToken.getPrincipal();
        String providerId = authenticationToken.getProviderId();
        SocialTypeEnum socialType =SocialTypeEnum.SOCIAL_TYPE_OPEN_ID;
        if(StringUtils.isNotBlank(providerId)){
            socialType = SocialTypeEnum.valueOf(providerId);
        }
        UserSocialEntity userSocialEntity = userSocialDao.findBySocialTypeAndSocialId(socialType,providerUserId);

        if(userSocialEntity == null){
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        String userId = userSocialEntity.getUserId();
        UserDetails user = userDetailsService.loadUserByUserId(userId);

        if(user == null ){
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        List<GrantedAuthority> list = new ArrayList<>();

        //这时候已经认证成功了
        OpenIdAuthenticationToken authenticationResult = new OpenIdAuthenticationToken(user, list);
        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    public UserDetailsServiceImpl getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public UserSocialDao getUserSocialDao() {
        return userSocialDao;
    }

    public void setUserSocialDao(UserSocialDao userSocialDao) {
        this.userSocialDao = userSocialDao;
    }
}
