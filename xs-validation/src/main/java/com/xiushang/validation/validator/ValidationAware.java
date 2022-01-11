package com.xiushang.validation.validator;

import java.util.List;


public interface ValidationAware<T> {
    /**Ap
     * 验证
     * @param dto
     * @param errors
     * @return
     */
    boolean validate(T dto, List<String> errors);
}
