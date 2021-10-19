package com.xiushang.marketing.test.tencent;

import com.tencent.ads.ApiContextConfig;
import com.tencent.ads.TencentAds;
import com.tencent.ads.model.OauthTokenResponseData;
import com.xiushang.marketing.test.config.TencentConfig;

public class Example {
    private static Long clientId = TencentConfig.CLIENT_ID; // 修改为你的clientId
    private static String clientSecret = TencentConfig.CLIENT_SECRET; // 修改为你的clientSecret
    private static String grantType = "authorization_code";
    private static String authorizationCode = TencentConfig.AUTHORIZATION_CODE; // 修改为你获取到的AUTHORIZATION CODE
    private static String redirectUri = "https://www.xiushangsh.com"; // 修改为你的回调地址

    public static void main(String[] args) {
        TencentAds tencentAds = TencentAds.getInstance();
        tencentAds.init(new ApiContextConfig());

        try {
            OauthTokenResponseData responseData = tencentAds.oauth()
                    .oauthToken(clientId, clientSecret, grantType, authorizationCode, null, redirectUri);
            if (responseData != null) {
                String accessToken = responseData.getAccessToken();
                System.out.println(accessToken);
                tencentAds.setAccessToken(accessToken);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
