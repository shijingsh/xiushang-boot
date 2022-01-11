package com.xiushang.validation.validator;

import javax.validation.constraints.BankCard;

import java.util.regex.Pattern;

/**
 * <p>
 * BankCardConstraintValidator
 * <p>
 *
 * @author: kancy
 * @date: 2020/4/20 11:08
 **/

public class BankCardConstraintValidator extends CheckEmptyConstraintValidator<BankCard, Object>{
    /**
     * 验证的值不为空时，验证结果
     *
     * @param value
     * @return
     */
    @Override
    protected boolean check(Object value) {
        return Pattern.compile(annotation.regexp()).matcher(String.valueOf(value)).find();
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
