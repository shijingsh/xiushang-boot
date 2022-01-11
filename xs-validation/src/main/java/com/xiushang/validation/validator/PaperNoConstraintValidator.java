package com.xiushang.validation.validator;

import javax.validation.constraints.PaperNo;
import com.xiushang.validation.utils.IdCardUtils;

import java.util.regex.Pattern;


public class PaperNoConstraintValidator extends CheckEmptyConstraintValidator<PaperNo, String> {

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
