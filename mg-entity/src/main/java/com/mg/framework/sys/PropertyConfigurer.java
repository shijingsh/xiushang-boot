package com.mg.framework.sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 缓存所有配置数据的辅助类.
 * 直接调用getContextProperty方法就能简单获取在配置文件中的属性值。
 *
 */
public class PropertyConfigurer extends PropertyPlaceholderConfigurer {
    private static Map<String, Object> ctxPropMap;
    private static Logger logger = LoggerFactory.getLogger(PropertyConfigurer.class);

    //static method for accessing context properties
    public static Object getContextProperty(String name) {
        return ctxPropMap.get(name);
    }

    public static String getConfig(String key)
    {
        if(ctxPropMap==null)return null;
        return (String)ctxPropMap.get(key);
    }
    public static String getConfig(String key,String defaultStr)
    {
        if(ctxPropMap==null || ctxPropMap.get(key)==null)return defaultStr;
        return (String)ctxPropMap.get(key);
    }

    public static Map<String, Object> getConfigurer() {
        return ctxPropMap;
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory,
                                     Properties props) throws BeansException {

        super.processProperties(beanFactory, props);
        //load properties to ctxPropMap
        ctxPropMap = new HashMap<String, Object>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropMap.put(keyStr, value);
        }
    }
}
