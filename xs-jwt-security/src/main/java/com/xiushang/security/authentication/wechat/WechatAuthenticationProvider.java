package com.xiushang.security.authentication.wechat;

import com.alibaba.fastjson.JSONObject;
import com.xiushang.security.SecurityUser;
import com.xiushang.service.impl.UserDetailsServiceImpl;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashSet;

/**
 * 微信认证提供者
 *
 */
@Data
public class WechatAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

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
        String code = (String) authenticationToken.getPrincipal();

        // 根据code 获取openid unionid

        JSONObject jsonObject = new JSONObject();
        String unionId = jsonObject.getString("unionid");
        String openId = jsonObject.getString("openid");
        SecurityUser securityUser = (SecurityUser) ((UserDetailsServiceImpl)userDetailsService).loadUserByOpenId(openId);
        if (securityUser == null) {
            // 微信用户不存在，注册成为新会员
            String sessionKey = "";
            String encryptedData = authenticationToken.getEncryptedData();
            String iv = authenticationToken.getIv();
            // 解密 encryptedData 获取用户信息

        }
        WechatAuthenticationToken result = new WechatAuthenticationToken(securityUser, securityUser.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return WechatAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
