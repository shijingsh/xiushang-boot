package com.xiushang.security.granter;

import com.xiushang.common.user.vo.WxLoginVo;
import com.xiushang.security.authentication.wechat.WechatAuthenticationToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  微信授权者
 *
 */
public class WechatTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = "wechat";
    private final AuthenticationManager authenticationManager;

    public WechatTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, AuthenticationManager authenticationManager) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

        String clientId = tokenRequest.getClientId();

        Map<String, String> parameters = new LinkedHashMap(tokenRequest.getRequestParameters());
        String code = parameters.get("code");

        String nickName = parameters.get("nickName");
        String avatarUrl = parameters.get("avatarUrl");
        String gender = parameters.get("gender");
        String email = parameters.get("email");
        String encryptedData = parameters.get("encryptedData");
        String iv = parameters.get("iv");

        WxLoginVo wxLoginVo = new WxLoginVo();
        wxLoginVo.setCode(code);
        wxLoginVo.setAvatarUrl(avatarUrl);
        wxLoginVo.setClientId(clientId);
        wxLoginVo.setNickName(nickName);
        wxLoginVo.setGender(gender);
        wxLoginVo.setEmail(email);
        wxLoginVo.setIv(iv);
        wxLoginVo.setEncryptedData(encryptedData);

        // 过河拆桥，移除后续无用参数
        parameters.remove("code");
        parameters.remove("encryptedData");
        parameters.remove("iv");
        parameters.remove("nickName");
        parameters.remove("avatarUrl");
        parameters.remove("gender");
        parameters.remove("email");

        Authentication userAuth = new WechatAuthenticationToken(wxLoginVo); // 未认证状态
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);

        try {
            userAuth = this.authenticationManager.authenticate(userAuth); // 认证中
        } catch (Exception e) {
            throw new InvalidGrantException(e.getMessage());
        }

        if (userAuth != null && userAuth.isAuthenticated()) { // 认证成功
            OAuth2Request storedOAuth2Request = this.getRequestFactory().createOAuth2Request(client, tokenRequest);
            return new OAuth2Authentication(storedOAuth2Request, userAuth);
        } else { // 认证失败
            throw new InvalidGrantException("Could not authenticate code: " + code);
        }
    }
}
