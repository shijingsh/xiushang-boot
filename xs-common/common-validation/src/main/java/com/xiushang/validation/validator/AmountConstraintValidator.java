package com.xiushang.validation.validator;

import javax.validation.constraints.Amount;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class AmountConstraintValidator extends CheckEmptyConstraintValidator<Amount, Object> {

    /**
     * 验证的值不为空时，验证结果
     * @param value
     * @return
     */
    @Override
    protected boolean check(Object value) {
        // 正则过滤
        if(!isEmpty(annotation.regexp())
            && !Pattern.compile(annotation.regexp()).matcher(String.valueOf(value)).find()){
            return false;
        }

        if (value instanceof Number){
            return doValidByNumber(value);
        }
        if (value instanceof CharSequence){
            return doValidByCharSequence((CharSequence) value);
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

    private boolean doValidByCharSequence(CharSequence value) {
        BigDecimal bigNum = this.getBigDecimalValue(value);
        if (bigNum == null) {
            return false;
        }
        return doValidByBigDecimal(bigNum);
    }

    private boolean doValidByNumber(Object value) {
        Number num = Number.class.cast(value);
        BigDecimal bigNum;
        if (num instanceof BigDecimal) {
            bigNum = (BigDecimal)num;
        } else {
            bigNum = (new BigDecimal(num.toString())).stripTrailingZeros();
        }
        return doValidByBigDecimal(bigNum);
    }

    private boolean doValidByBigDecimal(BigDecimal bigNum) {
        boolean validResult = false;
        // 整数和小数部分长度校验
        if(annotation.integer() > 0 ){
            int integerPartLength = bigNum.precision() - bigNum.scale();
            validResult = annotation.integer() >= integerPartLength;
            if(!validResult){
                return false;
            }
        }
        if(annotation.fraction() > 0 ){
            int fractionPartLength = bigNum.scale() < 0 ? 0 : bigNum.scale();
            validResult = annotation.fraction() >= fractionPartLength;
            if(!validResult){
                return false;
            }
        }
        // 大小校验
        if (bigNum.doubleValue() > annotation.max()){
            return false;
        }
        if (bigNum.doubleValue() < annotation.min()){
            return false;
        }

        // 通过以上校验，再校验是否可以为0值
        return !(!annotation.canZero() && bigNum.doubleValue() == 0);
    }

    private BigDecimal getBigDecimalValue(CharSequence charSequence) {
        try {
            BigDecimal bd = new BigDecimal(charSequence.toString());
            return bd;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
