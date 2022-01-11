package com.xiushang.validation.validator;

import java.lang.annotation.Annotation;

/**
 * <p>
 * CheckEmptyConstraintValidator
 * <p>
 *
 * @author: kancy
 * @date: 2020/4/20 10:21
 **/

public abstract class CheckEmptyConstraintValidator<A extends Annotation, T> extends HibernateConstraintValidator<A, T> {

    @Override
    protected boolean isValid(T value) {
        if(isEmpty(value)){
            setEmpty(true);
            return requestEmptyResult();
        }
        return check(value);
    }

    /**
     * 验证的值不为空时，验证结果
     * @param value
     * @return
     */
    protected abstract boolean check(T value);

    /**
     * 验证的值为空时，返回结果
     * @return
     */
    protected abstract boolean requestEmptyResult();
}
