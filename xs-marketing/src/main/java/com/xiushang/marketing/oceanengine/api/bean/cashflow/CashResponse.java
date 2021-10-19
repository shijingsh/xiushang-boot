package com.xiushang.marketing.oceanengine.api.bean.cashflow;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;


@Data
public class CashResponse extends OceanEngineResponse<CashResponse.Data> {

    @lombok.Data
    public static class Data {
        String transaction_seq;
    }
}
