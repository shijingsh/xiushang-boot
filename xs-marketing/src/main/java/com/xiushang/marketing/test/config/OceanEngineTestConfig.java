package com.xiushang.marketing.test.config;

import com.xiushang.marketing.oceanengine.support.OceanEngineConfig;

public class OceanEngineTestConfig extends OceanEngineConfig {

    public static final String ACCESS_TOKEN = "b77558d4f505cdf302f714c11cc7724706cdb278";
    public static final String REFRESH_TOKEN = "af6aebe189b5278156008e5bc92871b8ca6db303";

    public static final String AUTHORIZATION_CODE = "368860bb4ea1362cae72bcb38a9647a6c74c8ee3"; // 修改为你获取到的AUTHORIZATION CODE

    public OceanEngineTestConfig() {
        this.setAppId("1712303220618248");
        this.setAppSecret("83832860ac9d653420990b9ad1689d37b921adb4");
        this.setRedirectUrl("https://www.xiushangsh.com");
        this.setWelcomeUrl("https://www.xiushangsh.com");
    }
}
