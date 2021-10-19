package com.xiushang.marketing.oceanengine.support;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
@Accessors(chain = true)
public class OceanEngineConfig {
    private String appId;
    private String appSecret;
    private String redirectUrl;
    private String welcomeUrl;
}
