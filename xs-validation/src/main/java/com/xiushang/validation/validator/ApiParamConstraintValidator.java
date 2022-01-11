package com.xiushang.validation.validator;

import com.xiushang.validation.utils.AssertUtils;
import io.swagger.annotations.ApiParam;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ApiParamConstraintValidator implements ConstraintValidator<ApiParam, Object> {

    private String msg = null;

    @Override
    public void initialize(ApiParam constraintAnnotation) {

        String value = constraintAnnotation.value();
        this.msg = String.format("%s不能为空!", value);
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
