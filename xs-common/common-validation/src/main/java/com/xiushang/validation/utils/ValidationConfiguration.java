package com.xiushang.validation.utils;

import org.springframework.boot.validation.MessageInterpolatorFactory;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidationConfiguration {


    public static org.springframework.validation.Validator getValidator() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("utf-8");// 读取配置文件的编码格式
        messageSource.setCacheMillis(-1);// 缓存时间，-1表示不过期
        messageSource.setBasename("ValidationMessages");// 配置文件前缀名，设置为Messages,那你的配置文件必须以Messages.properties/Message_en.properties...


        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        MessageInterpolatorFactory interpolatorFactory = new MessageInterpolatorFactory();
        factoryBean.setMessageInterpolator(interpolatorFactory.getObject());
        factoryBean.setValidationMessageSource(messageSource);

        factoryBean.getValidationPropertyMap().put("hibernate.validator.fail_fast", "true");

        return factoryBean;
    }
}
