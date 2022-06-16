package com.xiushang.validation.validator;

import javax.validation.constraints.YesOrNo;

public class YesOrNoConstraintValidator extends CheckEmptyConstraintValidator<YesOrNo, Object> {

    /**
     * 验证的值不为空时，验证结果
     * @param value
     * @return
     */
    @Override
    protected boolean check(Object value) {
        return String.valueOf(value).equals(annotation.yes())
                || String.valueOf(value).equals(annotation.no());
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
