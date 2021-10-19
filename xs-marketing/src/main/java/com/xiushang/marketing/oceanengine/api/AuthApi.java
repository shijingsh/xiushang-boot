package com.xiushang.marketing.oceanengine.api;

import com.alibaba.fastjson.JSON;
import com.xiushang.marketing.oceanengine.api.bean.auth.*;
import com.xiushang.marketing.oceanengine.support.*;

import java.util.List;


public class AuthApi extends OceanEngineResource {

    public static String buildAuthorizationUrl(OceanEngineConfig conf, String redirectURI, List<Integer> scopeList,
                                               String state) {
        String scope = JSON.toJSONString(scopeList);
        return String.format(UrlConst.CONNECT_OAUTH2_AUTHORIZE_URL, conf.getAppId(), state,
                scope, redirectURI);
    }

    public static AccessTokenResponse getAccessToken(OceanEngineConfig conf, String authCode) throws OceanEngineRestException {
        AccessTokenRequest request = new AccessTokenRequest();
        request.setApp_id(conf.getAppId());
        request.setSecret(conf.getAppSecret());
        request.setGrant_type("auth_code");
        request.setAuth_code(authCode);
        String payLoad = request.toJSON();
        return execute(HttpMethod.POST, UrlConst.OAUTH2_ACCESS_TOKEN_URL, payLoad, AccessTokenResponse.class, null);

    }

    public static RefreshTokenResponse refreshAccessToken(OceanEngineConfig conf, String refreshToken)
            throws OceanEngineRestException {
        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setApp_id(conf.getAppId());
        request.setSecret(conf.getAppSecret());
        request.setGrant_type("refresh_token");
        request.setRefresh_token(refreshToken);
        return execute(HttpMethod.POST, UrlConst.OAUTH2_REFRESH_TOKEN_URL, request.toJSON(), RefreshTokenResponse.class, null);
    }

    public static AuthAdvertiserResponse listAuthAdvertiser(OceanEngineConfig conf, String accessToken) {
        String url = String.format(UrlConst.OAUTH2_AUTH_ADVERTISER_URL, accessToken, conf.getAppId(), conf.getAppSecret());
        return execute(HttpMethod.GET, url, "", AuthAdvertiserResponse.class, null);
    }
}
