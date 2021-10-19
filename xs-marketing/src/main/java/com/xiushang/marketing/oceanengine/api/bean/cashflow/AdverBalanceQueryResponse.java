package com.xiushang.marketing.oceanengine.api.bean.cashflow;


import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class AdverBalanceQueryResponse extends OceanEngineResponse<AdverBalanceQueryResponse.BalanceData> {

    @Data
    public static class BalanceData {
        String _id;
        Long advertiser_id;
        String name;
        String email;
        BigDecimal balance;
        BigDecimal valid_balance;
        BigDecimal cash;
        BigDecimal valid_cash;
        BigDecimal grant;
        BigDecimal valid_grant;
        BigDecimal return_goods_abs;
        BigDecimal valid_return_goods_abs;
        String updateTime;
        Long adverid;
        Long agentid;
    }
}
