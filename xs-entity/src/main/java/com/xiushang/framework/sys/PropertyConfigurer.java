package com.xiushang.framework.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * 缓存所有配置数据的辅助类.
 */
public class PropertyConfigurer {

    @Autowired
    public static Environment env;

    public static String getConfig(String key)
    {
        return env.getProperty(key);
    }

    public static String getContextProperty(String key) {
        return env.getProperty(key);
    }
}
