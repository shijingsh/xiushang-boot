package com.xiushang.security.granter;

import com.xiushang.common.user.vo.SocialLoginVo;
import com.xiushang.common.user.vo.WxLoginVo;
import com.xiushang.security.authentication.social.SocialAuthenticationToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 社交账号授权者
 *
 */
public class SocialTokenGranter extends AbstractTokenGranter {


    private static final String GRANT_TYPE = "social_type";
    private final AuthenticationManager authenticationManager;

    public SocialTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
                              OAuth2RequestFactory requestFactory, AuthenticationManager authenticationManager
    ) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

        Map<String, String> parameters = new LinkedHashMap(tokenRequest.getRequestParameters());

        String socialType = parameters.get("socialType"); // 社交账号类型
        String socialId = parameters.get("socialId"); // 社交账号ID
        String clientId = parameters.get("client_id");

        String nickName = parameters.get("nickName");
        String avatarUrl = parameters.get("avatarUrl");
        String gender = parameters.get("gender");
        String email = parameters.get("email");
        String mobile = parameters.get("mobile");


        SocialLoginVo loginVo = new SocialLoginVo();
        loginVo.setSocialId(socialId);
        loginVo.setSocialType(socialType);
        loginVo.setAvatarUrl(avatarUrl);
        loginVo.setClientId(clientId);
        loginVo.setNickName(nickName);
        loginVo.setGender(gender);
        loginVo.setEmail(email);
        loginVo.setMobile(mobile);

        parameters.remove("socialType");
        parameters.remove("socialId");
        parameters.remove("nickName");
        parameters.remove("avatarUrl");
        parameters.remove("gender");
        parameters.remove("email");

        Authentication userAuth = new SocialAuthenticationToken(loginVo);
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);

        try {
            userAuth = this.authenticationManager.authenticate(userAuth);
        } catch (AccountStatusException var8) {
            throw new InvalidGrantException(var8.getMessage());
        } catch (BadCredentialsException var9) {
            throw new InvalidGrantException(var9.getMessage());
        }

        if (userAuth != null && userAuth.isAuthenticated()) {
            OAuth2Request storedOAuth2Request = this.getRequestFactory().createOAuth2Request(client, tokenRequest);
            return new OAuth2Authentication(storedOAuth2Request, userAuth);
        } else {
            throw new InvalidGrantException("Could not authenticate user socialType " + socialType+" socialId"+socialId);
        }
    }
}
