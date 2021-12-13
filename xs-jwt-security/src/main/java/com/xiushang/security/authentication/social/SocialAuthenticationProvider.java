package com.xiushang.security.authentication.social;

import com.xiushang.entity.UserSocialEntity;
import com.xiushang.jpa.repository.UserSocialDao;
import com.xiushang.security.SecurityUser;
import com.xiushang.security.authentication.TenantProvider;
import com.xiushang.service.impl.UserDetailsServiceImpl;
import com.xiushang.util.SocialTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


public class SocialAuthenticationProvider extends TenantProvider implements AuthenticationProvider {

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
        SecurityUser securityUser = (SecurityUser)((UserDetailsServiceImpl)getUserDetailsService()).loadUserByUserId(userId);

        if(securityUser == null ){
            throw new InternalAuthenticationServiceException("无法获取用户社交账号信息");
        }

        //设置租户信息
        super.settingTenantId(securityUser, authenticationToken);

        //这时候已经认证成功了
        SocialAuthenticationToken authenticationResult = new SocialAuthenticationToken(authenticationToken.getClientId(),securityUser, securityUser.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    public UserSocialDao getUserSocialDao() {
        return userSocialDao;
    }

    public void setUserSocialDao(UserSocialDao userSocialDao) {
        this.userSocialDao = userSocialDao;
    }
}
