package com.xiushang.framework.utils;


import com.xiushang.framework.model.BaseInnerEnum;

/**
 * 有效、无效枚举值
 * @author liukefu
 */
public class DeleteEnum extends BaseInnerEnum {

    public static final DeleteEnum instance = new DeleteEnum();

    public static final int VALID = 0;
    public static final int INVALID = 1;

    private DeleteEnum() {
        ENUM(VALID, "有效");
        ENUM(INVALID, "无效");
    }

}
