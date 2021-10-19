package com.xiushang.marketing.oceanengine.api.bean.advertiser;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import com.xiushang.marketing.oceanengine.support.code.BudgetMode;

import java.math.BigDecimal;
import java.util.List;


public class AdverBudgetResponse extends OceanEngineResponse<List<AdverBudgetResponse.Data>> {

    @lombok.Data
    public static class Data {
        List<AdverBudgetInfo> list;
    }

    @lombok.Data
    public static class AdverBudgetInfo {
        private Long advertiser_id;
        private BigDecimal budget;
        private BudgetMode budget_mode;
    }
}
