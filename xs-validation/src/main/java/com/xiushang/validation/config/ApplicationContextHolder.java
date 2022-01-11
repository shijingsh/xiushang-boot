package com.xiushang.validation.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * <p>
 * ApplicationContextHolder
 * <p>
 *
 * @author: kancy
 * @date: 2020/3/6 22:11
 **/
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        ApplicationContextHolder.applicationContext = applicationContext;
    }

    public static ApplicationContext getContext() {
        return applicationContext;
    }
}
