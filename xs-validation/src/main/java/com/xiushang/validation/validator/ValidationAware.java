package com.xiushang.validation.validator;

import java.util.List;

/**
 * <p>
 * ValidationAware
 * <p>
 *
 * @author kancy
 * @see 2020/9/4 15:44
 **/

public interface ValidationAware<T> {
    /**Ap
     * 验证
     * @param dto
     * @param errors
     * @return
     */
    boolean validate(T dto, List<String> errors);
}
