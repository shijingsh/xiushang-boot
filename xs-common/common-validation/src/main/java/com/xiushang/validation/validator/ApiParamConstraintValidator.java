package com.xiushang.validation.validator;

import com.xiushang.validation.utils.AssertUtils;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ApiParamConstraintValidator implements ConstraintValidator<ApiParam, Object> {

    private String msg = null;
    private boolean required = false;

    @Override
    public void initialize(ApiParam constraintAnnotation) {

        String message = constraintAnnotation.message();
        String value = constraintAnnotation.value();
        required = constraintAnnotation.required();

        if(StringUtils.isBlank(value)){
            value = constraintAnnotation.name();
        }

        if(StringUtils.isNotBlank(message)){
            this.msg = message;
        }else{
            this.msg = String.format("%s不能为空!", value);
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if(AssertUtils.notEmpty(value,required)){
            return true;
        }


        if (context.getDefaultConstraintMessageTemplate().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.msg).addConstraintViolation();
        }
        return false;
    }


}
