package com.xiushang.validation.validator;

import org.springframework.util.StringUtils;

import javax.validation.constraints.UserName;
import java.util.regex.Pattern;

public class UserNameConstraintValidator extends CheckEmptyConstraintValidator<UserName, String> {

    /**
     * 验证的值不为空时，验证结果
     *
     * @param value
     * @return
     */
    @Override
    protected boolean check(String value) {
        // 长度
        if(value.length() < annotation.min() || value.length() > annotation.max()){
            return false;
        }
        // 正则匹配
        if(!StringUtils.isEmpty(annotation.regexp())){
            return Pattern.compile(annotation.regexp()).matcher(value).find();
        }
        return true;
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
