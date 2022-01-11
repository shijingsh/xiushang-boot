package com.xiushang.validation.validator;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Map;

public class ApiModelPropertyConstraintValidator implements ConstraintValidator<ApiModelProperty, Object> {

    private String msg = null;

    @Override
    public void initialize(ApiModelProperty constraintAnnotation) {

        String value = constraintAnnotation.value();
        this.msg = String.format("%s不能为空!", value);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if(value instanceof  String){
            if (StringUtils.isNotBlank((String)value)) {
                return true;
            }
        }

        if(value instanceof Map){
            if(value!=null && ((Map)value).size()>0){
                return true;
            }
        }
        if(value instanceof List){
            if(value!=null && ((List)value).size()>0){
                return true;
            }
        }


        if(value != null){
            return true;
        }


        if (context.getDefaultConstraintMessageTemplate().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.msg).addConstraintViolation();
        }
        return false;
    }


}
