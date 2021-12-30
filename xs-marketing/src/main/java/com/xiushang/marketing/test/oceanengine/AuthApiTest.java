package com.xiushang.marketing.test.oceanengine;

import com.alibaba.fastjson.JSON;
import com.xiushang.marketing.oceanengine.api.AuthApi;
import com.xiushang.marketing.oceanengine.api.bean.auth.AccessTokenResponse;
import com.xiushang.marketing.oceanengine.api.bean.auth.AuthAdvertiserResponse;
import com.xiushang.marketing.oceanengine.api.bean.auth.RefreshTokenResponse;
import com.xiushang.marketing.test.config.OceanEngineTestConfig;

public class AuthApiTest {

    public static void main(String[] args) {
        try {
            OceanEngineTestConfig config = new OceanEngineTestConfig();
            /*
            AccessTokenResponse accessToken = AuthApi.getAccessToken(config,OceanEngineTestConfig.AUTHORIZATION_CODE);
            System.out.println("=============================accessToken=============================");
            System.out.println(JsonUtils.toJsonStr(accessToken));
            */

            /*
            RefreshTokenResponse refreshTokenResponse = AuthApi.refreshAccessToken(config,"df37e39f78aa3a89bd85a40a0dec605636ba2cab");
            System.out.println("=============================refreshTokenResponse=============================");
            System.out.println(JsonUtils.toJsonStr(refreshTokenResponse));
            */

            /**/
            AuthAdvertiserResponse authAdvertiserResponse = AuthApi.listAuthAdvertiser(config,OceanEngineTestConfig.ACCESS_TOKEN);
            System.out.println("=============================authAdvertiserResponse=============================");
            System.out.println(JSON.toJSONString(authAdvertiserResponse));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
