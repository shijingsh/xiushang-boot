package com.xiushang.validation.validator;

import com.xiushang.validation.utils.IdCardUtils;

import javax.validation.constraints.IdCard;
import java.util.regex.Pattern;


public class IdCardConstraintValidator extends CheckEmptyConstraintValidator<IdCard, String> {

    /**
     * 验证的值不为空时，验证结果
     * @param value
     * @return
     */
    @Override
    protected boolean check(String value) {
        if (annotation.force()){
            return IdCardUtils.validateCard(value);
        }
        return Pattern.compile(annotation.regexp()).matcher(value).find();
    }

    /**
     * 验证的值为空时，返回结果
     *
     * @return
     */
    @Override
    protected boolean requestEmptyResult() {
        return !annotation.required();
    }
}
