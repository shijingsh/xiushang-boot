package com.xiushang.marketing.oceanengine.support.code;

import java.util.Arrays;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
public enum AdStatus {
    UNKNOWN("未知"),
    AD_STATUS_DELIVERY_OK("投放中"),
    AD_STATUS_DATA_ERROR("数据错误"),
    AD_STATUS_DISABLE("计划暂停"),
    AD_STATUS_AUDIT("新建审核中"),
    AD_STATUS_REAUDIT("修改审核中"),
    AD_STATUS_DONE("已完成（投放达到结束时间）"),
    AD_STATUS_CREATE("计划新建"),
    AD_STATUS_AUDIT_DENY("审核不通过"),
    AD_STATUS_BALANCE_EXCEED("账户余额不足"),
    AD_STATUS_BUDGET_EXCEED("超出预算"),
    AD_STATUS_NOT_START("未到达投放时间"),
    AD_STATUS_NO_SCHEDULE("不在投放时段"),
    AD_STATUS_CAMPAIGN_DISABLE("已被广告组暂停"),
    AD_STATUS_CAMPAIGN_EXCEED("广告组超出预算"),
    AD_STATUS_DELETE("已删除"),
    AD_STATUS_FROZEN("已冻结"),
    AD_STATUS_SOME_DELIVERY_OK("部分投放中"),
    AD_STATUS_ALL("所有包含已删除"),
    AD_STATUS_NOT_DELETE("所有不包含已删除（状态过滤默认值）"),
    AD_STATUS_ADVERTISER_BUDGET_EXCEED("超出账户日预算"),
    ;

    public String desc;

    AdStatus(String desc) {
        this.desc = desc;
    }

    public static AdStatus lookup(String s) {
        return Arrays.stream(values()).filter(v -> s.equals(v.name())).findAny().orElse(UNKNOWN);
    }
}
