package com.xiushang.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于配置不需要保护的资源路径
 * @author ZKUI
 * @version V1.0
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt.ignored")
public class JWTIgnoreUrlsConfig {

    private List<String> urls = new ArrayList<>();
}
