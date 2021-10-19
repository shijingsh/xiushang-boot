package com.xiushang.marketing.oceanengine.support;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class OceanEngineConfig {
    private String appId;
    private String appSecret;
    private String redirectUrl;
    private String welcomeUrl;
}
