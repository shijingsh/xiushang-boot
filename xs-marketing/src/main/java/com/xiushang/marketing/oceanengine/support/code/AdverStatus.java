package com.xiushang.marketing.oceanengine.support.code;

import java.util.HashMap;
import java.util.Map;


public enum AdverStatus {
    STATUS_DISABLE, STATUS_PENDING_CONFIRM, STATUS_PENDING_VERIFIED, STATUS_CONFIRM_FAIL, STATUS_ENABLE,
    STATUS_CONFIRM_FAIL_END, STATUS_PENDING_CONFIRM_MODIFY, STATUS_CONFIRM_MODIFY_FAIL, STATUS_LIMIT,
    STATUS_WAIT_FOR_BPM_AUDIT, STATUS_WAIT_FOR_PUBLIC_AUTH, STATUS_SELF_SERVICE_UNAUDITED;

    static Map<AdverStatus, String> lookupTable = new HashMap<>();

    static {
        lookupTable.put(AdverStatus.STATUS_DISABLE, "已禁用");
        lookupTable.put(AdverStatus.STATUS_PENDING_CONFIRM, "	申请待审核");
        lookupTable.put(AdverStatus.STATUS_PENDING_VERIFIED, "待验证");
        lookupTable.put(AdverStatus.STATUS_CONFIRM_FAIL, "审核不通过");
        lookupTable.put(AdverStatus.STATUS_ENABLE, "已审核");
        lookupTable.put(AdverStatus.STATUS_CONFIRM_FAIL_END, "CRM审核不通过");
        lookupTable.put(AdverStatus.STATUS_PENDING_CONFIRM_MODIFY, "修改待审核");
        lookupTable.put(AdverStatus.STATUS_CONFIRM_MODIFY_FAIL, "修改审核不通过");
        lookupTable.put(AdverStatus.STATUS_LIMIT, "限制");
        lookupTable.put(AdverStatus.STATUS_WAIT_FOR_BPM_AUDIT, "等待CRM审核");
        lookupTable.put(AdverStatus.STATUS_WAIT_FOR_PUBLIC_AUTH, "待对公验证");
        lookupTable.put(AdverStatus.STATUS_SELF_SERVICE_UNAUDITED, "--");
    }

    public static String mapToName(AdverStatus status) {
        return lookupTable.getOrDefault(status, "未知");
    }

}
