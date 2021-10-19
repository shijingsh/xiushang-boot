package com.xiushang.marketing.oceanengine.api.bean.advertiser;

import com.xiushang.marketing.oceanengine.api.bean.BaseModel;

import lombok.Data;


@Data
public class UpdateBudgetRequest extends BaseModel {
    /**
     * "广告主ID", required = true)
     */
    Long advertiser_id;
    /**
     * "预算类型，不限：BUDGET_MODE_INFINITE , 天预算：BUDGET_MODE_DAY ", required = true)
     */
    String budget_mode;
    /**
     * "预算值，数字", required = true)
     */
    Float budget;
}
