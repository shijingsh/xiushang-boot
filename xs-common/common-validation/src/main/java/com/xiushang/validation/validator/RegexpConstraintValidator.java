package com.xiushang.validation.validator;

import javax.validation.constraints.Regexp;

import java.util.regex.Pattern;



public class RegexpConstraintValidator extends CheckEmptyConstraintValidator<Regexp, Object>{
    /**
     * 验证的值不为空时，验证结果
     *
     * @param value
     * @return
     */
    @Override
    protected boolean check(Object value) {
        return Pattern.compile(annotation.value()).matcher(String.valueOf(value)).find();
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
