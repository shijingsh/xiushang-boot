package com.xiushang.validation.validator;

import com.xiushang.validation.utils.AssertUtils;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ApiModelPropertyConstraintValidator implements ConstraintValidator<ApiModelProperty, Object> {

    private String msg = null;

    @Override
    public void initialize(ApiModelProperty constraintAnnotation) {

        String message = constraintAnnotation.message();
        String value = constraintAnnotation.value();
        if(StringUtils.isBlank(value)){
            value = constraintAnnotation.name();
        }
        if(StringUtils.isBlank(value)){
            value = constraintAnnotation.notes();
        }
        if(StringUtils.isNotBlank(message)){
            this.msg = message;
        }else{
            this.msg = String.format("%s不能为空!", value);
        }

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if(AssertUtils.notEmpty(value)){
            return true;
        }


        if (context.getDefaultConstraintMessageTemplate().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.msg).addConstraintViolation();
        }
        return false;
    }


}
