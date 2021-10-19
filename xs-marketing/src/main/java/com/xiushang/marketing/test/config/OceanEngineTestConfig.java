package com.xiushang.marketing.test.config;

import com.xiushang.marketing.oceanengine.support.OceanEngineConfig;

public class OceanEngineTestConfig extends OceanEngineConfig {

    public static final String ACCESS_TOKEN = "xxxxxx";
    public static final String REFRESH_TOKEN = "xxxxxx";

    public static final String AUTHORIZATION_CODE = "xxxxx"; // 修改为你获取到的AUTHORIZATION CODE

    public OceanEngineTestConfig() {
        this.setAppId("xxxx");
        this.setAppSecret("xxx");
        this.setRedirectUrl("https://www.xxx.com");
        this.setWelcomeUrl("https://www.xxx.com");
    }
}
