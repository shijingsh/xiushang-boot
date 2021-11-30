package com.xiushang.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 用于配置oauth保护的资源路径
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "oauth.path")
public class OAuth2UrlConfig {

    private String url ="/api/";

}
