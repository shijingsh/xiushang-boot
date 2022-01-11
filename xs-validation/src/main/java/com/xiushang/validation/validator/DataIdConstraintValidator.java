package com.xiushang.validation.validator;

import javax.validation.constraints.DataId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * ConstraintValidator
 * </p>
 *
 * @author: kancy
 * @date: 2019/12/11 10:40
 **/
public class DataIdConstraintValidator extends CheckEmptyConstraintValidator<DataId, Object> {
    private static Logger log = LoggerFactory.getLogger(DataIdConstraintValidator.class);

    /**
     * 验证的值不为空时，验证结果
     * @param value
     * @return
     */
    @Override
    protected boolean check(Object value) {
        // 数据ID的校验规则
        Long longValue = null;
        // 1. Long or Integer 整数
        try {
            if(value instanceof Long){
                longValue = (Long) value;
            }else{
                longValue = Long.parseLong(String.valueOf(value));
            }
        } catch (Exception e) {
            log.error("DataId check value is not number.");
            return false;
        }

        // 2.范围
        return !(longValue < annotation.min() || longValue > annotation.max());
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
