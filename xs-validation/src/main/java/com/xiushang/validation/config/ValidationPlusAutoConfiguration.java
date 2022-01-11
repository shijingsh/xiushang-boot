package com.xiushang.validation.config;

import com.xiushang.validation.validator.AssertConstraintValidator;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * ValidConfig
 *
 * @author kancy
 * @date 2020/8/8 10:11
 */
public class ValidationPlusAutoConfiguration {

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        postProcessor.setValidator(validator());
        return postProcessor;
    }

    @Bean
    public Validator validator(){
        ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
                .configure()
                .failFast(true)
                .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        return validator;
    }

    @Bean
    public AssertConstraintValidator assertConstraintValidator(){
        return new AssertConstraintValidator();
    }

    @Bean
    public ApplicationContextHolder applicationContextHolder(){
        return new ApplicationContextHolder();
    }
}
