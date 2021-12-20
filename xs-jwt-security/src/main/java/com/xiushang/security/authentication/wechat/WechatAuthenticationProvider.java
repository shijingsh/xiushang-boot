package com.xiushang.security.authentication.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiushang.common.user.service.SystemParamService;
import com.xiushang.common.user.service.UserService;
import com.xiushang.common.user.vo.PhoneDecryptInfo;
import com.xiushang.common.user.vo.WxLoginVo;
import com.xiushang.common.utils.AESGetPhoneNumber;
import com.xiushang.common.utils.HttpClientUtil;
import com.xiushang.common.utils.JsonUtils;
import com.xiushang.entity.SystemParamEntity;
import com.xiushang.entity.UserEntity;
import com.xiushang.entity.UserSocialEntity;
import com.xiushang.entity.oauth.OauthClientDetailsEntity;
import com.xiushang.entity.shop.ShopEntity;
import com.xiushang.framework.sys.PropertyConfigurer;
import com.xiushang.jpa.repository.ShopDao;
import com.xiushang.jpa.repository.UserSocialDao;
import com.xiushang.security.SecurityUser;
import com.xiushang.security.authentication.TenantProvider;
import com.xiushang.service.impl.UserDetailsServiceImpl;
import com.xiushang.util.SocialTypeEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Date;

/**
 * 微信认证提供者
 *
 */
@Data
public class WechatAuthenticationProvider extends TenantProvider implements AuthenticationProvider {

    private SystemParamService systemParamService;
    private ShopDao shopDao;
    private UserSocialDao userSocialDao;
    private UserService userService;
    /**
     * 微信认证
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WechatAuthenticationToken authenticationToken = (WechatAuthenticationToken) authentication;
        WxLoginVo wxLoginVo = authenticationToken.getWxLoginVo();
        SecurityUser securityUser = null;
        //校验客户端
        String clientId = wxLoginVo.getClientId();
        SecurityUser tenantUser = (SecurityUser)((UserDetailsServiceImpl)getUserDetailsService()).loadUserByClientId(clientId);
        if (tenantUser == null) {
            throw new InternalAuthenticationServiceException("无法获取客户端信息");
        }
        //设置clientId对应的租户商铺
        ShopEntity shopEntity = shopDao.findByOwnerUser(tenantUser);
        if (shopEntity == null) {
            throw new InternalAuthenticationServiceException("客户端尚未开通商铺！");
        }
        //客户端信息
        OauthClientDetailsEntity clientDetailsEntity = getOauthClientDetailsDao().findByClientId(clientId);
        String code = wxLoginVo.getCode();

        String appid = clientDetailsEntity.getAppId();
        String secret = clientDetailsEntity.getSecret();
        if (StringUtils.isBlank(appid) || StringUtils.isBlank(secret)) {
            throw new InternalAuthenticationServiceException("客户端尚未设置AppId和AppSecret！");
        }
        // 根据code 获取openid unionid
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secret+"&js_code=" + code + "&grant_type=authorization_code";
        String json = HttpClientUtil.sendGetRequest(url);
        System.out.println("jscode2session返回：");
        System.out.println(json);
        JSONObject jsonObject = JSON.parseObject(json);
        String errcode = jsonObject.getString("errcode");
        if (StringUtils.isBlank(errcode)) {
            String unionId = jsonObject.getString("unionid");
            String openId = jsonObject.getString("openid");
            String sessionKey = jsonObject.getString("session_key");

            System.out.println("weixinLogin返回unionid："+unionId);
            System.out.println("weixinLogin返回openid："+openId);

            String mobile = null;
            if(StringUtils.isNotBlank(wxLoginVo.getEncryptedData()) && StringUtils.isNotBlank(wxLoginVo.getIv())){
                AESGetPhoneNumber aes = new AESGetPhoneNumber(wxLoginVo.getEncryptedData(),sessionKey,wxLoginVo.getIv());
                PhoneDecryptInfo info = aes.decrypt();
                if (null==info){
                    throw new InternalAuthenticationServiceException("解密微信手机号发生错误，会话超时！");
                }else {
                    System.out.println("======================解密微信手机号========================");
                    System.out.println(JsonUtils.toJsonStr(info));
                }
                if(info!=null && StringUtils.isNotBlank(info.getPhoneNumber())){
                    mobile = info.getPhoneNumber();
                }
            }

            if(StringUtils.isNotBlank(mobile)){
                //如果存在手机号码，则先查询手机号是否注册。
                securityUser =  (SecurityUser)((UserDetailsServiceImpl) getUserDetailsService()).loadUserByMobile(mobile);
            }

            UserSocialEntity userSocialEntity = null;
            if(StringUtils.isNotBlank(unionId)){
                userSocialEntity =  userSocialDao.findBySocialTypeAndSocialId(SocialTypeEnum.SOCIAL_TYPE_UNION_ID,unionId);
            }else{
                userSocialEntity =  userSocialDao.findBySocialTypeAndSocialId(SocialTypeEnum.SOCIAL_TYPE_OPEN_ID,openId);
            }

            if(userSocialEntity==null){

                if(securityUser==null){
                    //注册时，必须有手机号
                    if (StringUtils.isBlank(mobile)) {
                        throw new InternalAuthenticationServiceException("加密手机号数据encryptedData和iv必填！");
                    }
                    //不存在用户，则注册
                    UserEntity userEntity = new UserEntity();
                    userEntity.setEmail(wxLoginVo.getEmail());
                    userEntity.setHeadPortrait(wxLoginVo.getAvatarUrl());
                    userEntity.setMobile(mobile);
                    userEntity.setLoginName(mobile);        //手机号码作为登录名，password为空
                    userEntity.setName(wxLoginVo.getNickName());
                    userEntity.setLastLoginDate(new Date());
                    userEntity.setLastLoginPlatform(clientId);
                    userService.updateUser(userEntity);

                    securityUser = new SecurityUser(userEntity);
                }

               //生成社交账号信息
                if(StringUtils.isNotBlank(unionId)){
                    UserSocialEntity socialEntity = new UserSocialEntity();
                    socialEntity.setSocialId(unionId);
                    socialEntity.setSocialType(SocialTypeEnum.SOCIAL_TYPE_UNION_ID);

                    setUser(socialEntity,securityUser,wxLoginVo);

                    userSocialDao.save(socialEntity);
                }
                if(StringUtils.isNotBlank(openId)){
                    UserSocialEntity socialEntity = new UserSocialEntity();
                    socialEntity.setSocialId(openId);
                    socialEntity.setSocialType(SocialTypeEnum.SOCIAL_TYPE_OPEN_ID);
                    setUser(socialEntity,securityUser,wxLoginVo);

                    userSocialDao.save(socialEntity);
                }
            }else {
                securityUser =  (SecurityUser)((UserDetailsServiceImpl) getUserDetailsService()).loadUserByUserId(userSocialEntity.getUserId());
            }

        }else {
            throw new InternalAuthenticationServiceException("微信code无效或已过期！");
        }

        WechatAuthenticationToken result = new WechatAuthenticationToken(authenticationToken.getClientId(),securityUser, securityUser.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return WechatAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private void setUser(UserSocialEntity socialEntity,UserEntity userEntity,WxLoginVo wxLoginVo){
        socialEntity.setUserId(userEntity.getId());
        socialEntity.setClientId(wxLoginVo.getClientId());
        socialEntity.setAvatarUrl(wxLoginVo.getAvatarUrl());
        socialEntity.setEmail(wxLoginVo.getEmail());
        socialEntity.setGender(wxLoginVo.getGender());
        socialEntity.setNickName(wxLoginVo.getNickName());
    }

    public SystemParamService getSystemParamService() {
        return systemParamService;
    }

    public void setSystemParamService(SystemParamService systemParamService) {
        this.systemParamService = systemParamService;
    }

    public ShopDao getShopDao() {
        return shopDao;
    }

    public void setShopDao(ShopDao shopDao) {
        this.shopDao = shopDao;
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
}
