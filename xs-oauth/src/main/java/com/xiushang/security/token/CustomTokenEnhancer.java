package com.xiushang.security.token;

import com.xiushang.framework.log.SecurityConstants;
import com.xiushang.security.SecurityUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        final Map<String, Object> additionalInfo = new HashMap<>();

        Map<String, String>  map = authentication.getOAuth2Request().getRequestParameters();
        String clientId = authentication.getOAuth2Request().getClientId();

        if(map.get(SecurityConstants.AUTH_USER_ID_PARAM)!=null){
            additionalInfo.put("userId", map.get(SecurityConstants.AUTH_USER_ID_PARAM));
        }

        if(authentication.getPrincipal() instanceof SecurityUser){
            SecurityUser user = (SecurityUser) authentication.getPrincipal();
            additionalInfo.put("tenantId",user.getTenantId());
        }

        additionalInfo.put("clientId",clientId);

       // additionalInfo.put("company","bobo");
        // 注意添加的额外信息，最好不要和已有的json对象中的key重名，容易出现错误
        //additionalInfo.put("authorities", user.getAuthorities());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

        return accessToken;
    }
}
