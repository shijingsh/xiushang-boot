package com.xiushang.validation.validator;

import javax.validation.constraints.MobilePhone;

import java.util.regex.Pattern;


public class MobilePhoneConstraintValidator extends CheckEmptyConstraintValidator<MobilePhone, String> {

    /**
     * 验证的值不为空时，验证结果
     * @param value
     * @return
     */
    @Override
    protected boolean check(String value) {
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
