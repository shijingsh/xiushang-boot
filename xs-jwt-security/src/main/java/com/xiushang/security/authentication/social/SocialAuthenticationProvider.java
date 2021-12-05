package com.xiushang.security.authentication.social;

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


public class SocialAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsServiceImpl userDetailsService;

    private UserSocialDao userSocialDao;

    @Override
    public boolean supports(Class<?> authentication) {
        //支持SocialAuthenticationToken来验证
        return SocialAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //这个authentication就是SocialAuthenticationToken
        SocialAuthenticationToken authenticationToken = (SocialAuthenticationToken) authentication;

        //校验手机号
        String socialId = (String) authenticationToken.getPrincipal();
        String socialType = authenticationToken.getSocialType();
        SocialTypeEnum socialTypeEnum =SocialTypeEnum.SOCIAL_TYPE_OPEN_ID;
        if(StringUtils.isNotBlank(socialType)){
            socialTypeEnum = SocialTypeEnum.valueOf(socialType);
        }
        UserSocialEntity userSocialEntity = userSocialDao.findBySocialTypeAndSocialId(socialTypeEnum,socialId);

        if(userSocialEntity == null){
            throw new InternalAuthenticationServiceException("无法获取用户社交账号信息");
        }

        String userId = userSocialEntity.getUserId();
        UserDetails user = userDetailsService.loadUserByUserId(userId);

        if(user == null ){
            throw new InternalAuthenticationServiceException("无法获取用户社交账号信息");
        }

        List<GrantedAuthority> list = new ArrayList<>();

        //这时候已经认证成功了
        SocialAuthenticationToken authenticationResult = new SocialAuthenticationToken(user, list);
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