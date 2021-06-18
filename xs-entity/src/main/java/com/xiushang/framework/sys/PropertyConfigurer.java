package com.xiushang.framework.sys;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 缓存所有配置数据的辅助类.
 */
@Configuration
public class PropertyConfigurer implements EnvironmentAware {

    public static Environment env;

    public static String getConfig(String key)
    {
        return env.getProperty(key);
    }

    public static String getContextProperty(String key) {
        return env.getProperty(key);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.env=environment;
    }
}
