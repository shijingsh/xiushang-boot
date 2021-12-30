package com.xiushang.framework.sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * MgApplicationContext
 * web容器
 */
public class MgApplicationContext implements ApplicationContextAware {
    private static Logger logger = LoggerFactory.getLogger(MgApplicationContext.class);
    private static ApplicationContext ac;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ac = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return ac;
    }

    public static <T> T getBean(Class<T> cls) {
        if(ac != null)
            return ac.getBean(cls);

        return null;
    }
}
