package com.xiushang.ads.test;

import com.tencent.ads.ApiContextConfig;
import com.tencent.ads.TencentAds;
import com.tencent.ads.model.OauthTokenResponseData;

public class Example {
    private static Long clientId = 0L; // 修改为你的clientId
    private static String clientSecret = ""; // 修改为你的clientSecret
    private static String grantType = "authorization_code";
    private static String authorizationCode = "YOUR AUTHORIZATION CODE"; // 修改为你获取到的AUTHORIZATION CODE
    private static String redirectUri = "YOUR REDIRECT URI"; // 修改为你的回调地址

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
