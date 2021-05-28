package com.mg.framework.utils;


import com.mg.framework.model.BaseInnerEnum;

/**
 * 有效、无效枚举值
 * @author liukefu
 */
public class StatusEnum extends BaseInnerEnum {

    public static final StatusEnum instance = new StatusEnum();

    public static final int STATUS_VALID = 1;
    public static final int STATUS_INVALID = 0;

    private StatusEnum() {
        ENUM(STATUS_VALID, "有效");
        ENUM(STATUS_INVALID, "无效");
    }

}
