package com.xiushang.security.authentication.social;

import com.xiushang.common.user.service.UserService;
import com.xiushang.common.user.vo.SocialLoginVo;
import com.xiushang.common.user.vo.WxLoginVo;
import com.xiushang.entity.UserEntity;
import com.xiushang.entity.UserSocialEntity;
import com.xiushang.jpa.repository.UserSocialDao;
import com.xiushang.security.SecurityRole;
import com.xiushang.security.SecurityRoleVo;
import com.xiushang.security.SecurityUser;
import com.xiushang.security.authentication.TenantProvider;
import com.xiushang.service.impl.UserDetailsServiceImpl;
import com.xiushang.util.SocialTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SocialAuthenticationProvider extends TenantProvider implements AuthenticationProvider {

    private UserSocialDao userSocialDao;
    private UserService userService;
    @Override
    public boolean supports(Class<?> authentication) {
        //支持SocialAuthenticationToken来验证
        return SocialAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //这个authentication就是SocialAuthenticationToken
        SocialAuthenticationToken authenticationToken = (SocialAuthenticationToken) authentication;

        SocialLoginVo loginVo = authenticationToken.getSocialLoginVo();
        String clientId = loginVo.getClientId();
        SecurityUser securityUser = null;
        //校验ID
        String socialId = loginVo.getSocialId();
        String socialType = loginVo.getSocialType();
        SocialTypeEnum socialTypeEnum =SocialTypeEnum.SOCIAL_TYPE_OPEN_ID;
        if(StringUtils.isNotBlank(socialType)){
            socialTypeEnum = SocialTypeEnum.valueOf(socialType);
        }
        UserSocialEntity userSocialEntity = userSocialDao.findBySocialTypeAndSocialId(socialTypeEnum,socialId);

        if(userSocialEntity == null){

            //注册时，必须有手机号
            if (StringUtils.isBlank(loginVo.getMobile())) {
                throw new InternalAuthenticationServiceException("手机号码必填！");
            }
            //不存在用户，则注册
            UserEntity userEntity = new UserEntity();
            userEntity.setEmail(loginVo.getEmail());
            userEntity.setHeadPortrait(loginVo.getAvatarUrl());
            userEntity.setMobile(loginVo.getMobile());
            userEntity.setLoginName(loginVo.getMobile());        //手机号码作为登录名，password为空
            userEntity.setName(loginVo.getNickName());
            userEntity.setLastLoginDate(new Date());
            userEntity.setLastLoginPlatform(clientId);
            userService.registerUser(userEntity);

            securityUser = new SecurityUser(userEntity);

            //生成社交账号信息
            if(StringUtils.isNotBlank(loginVo.getSocialId())){
                UserSocialEntity socialEntity = new UserSocialEntity();
                socialEntity.setSocialId(loginVo.getSocialId());
                socialEntity.setSocialType(socialTypeEnum);

                saveSocialInfo(socialEntity,userEntity,loginVo);
            }

        }else {
            securityUser =  (SecurityUser)((UserDetailsServiceImpl) getUserDetailsService()).loadUserByUserId(userSocialEntity.getUserId());
            //更新社交账号资料
            saveSocialInfo(userSocialEntity,securityUser,loginVo);;
        }

        if(securityUser == null ){
            throw new InternalAuthenticationServiceException("无法获取用户社交账号信息");
        }

        //设置租户信息
        super.settingTenantId(securityUser, clientId);

        //设置附加权限
        List<SecurityRoleVo> list = new ArrayList<>();
        list.add(new SecurityRoleVo(SecurityRole.ROLE_USER));
        if(super.isAdminClient(clientId)){
            list.add(new SecurityRoleVo(SecurityRole.ROLE_CLIENT_ADMIN));
        }
        //这时候已经认证成功了
        SocialAuthenticationToken authenticationResult = new SocialAuthenticationToken(authenticationToken.getClientId(),securityUser
                , securityUser.getAuthorities(list));
        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    public UserSocialDao getUserSocialDao() {
        return userSocialDao;
    }

    public void setUserSocialDao(UserSocialDao userSocialDao) {
        this.userSocialDao = userSocialDao;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private void saveSocialInfo(UserSocialEntity socialEntity, UserEntity userEntity, SocialLoginVo loginVo){

        socialEntity.setUserId(userEntity.getId());
        socialEntity.setClientId(loginVo.getClientId());
        socialEntity.setAvatarUrl(loginVo.getAvatarUrl());
        socialEntity.setEmail(loginVo.getEmail());
        socialEntity.setGender(loginVo.getGender());
        socialEntity.setNickName(loginVo.getNickName());

        userSocialDao.save(socialEntity);
    }
}
