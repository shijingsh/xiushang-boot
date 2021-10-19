package com.xiushang.marketing.oceanengine.api.bean.cashflow;


import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import com.xiushang.marketing.oceanengine.support.Paging;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class AdverFundDetailResponse extends OceanEngineResponse<AdverFundDetailResponse.Data> {

    private Paging page_info;

    @lombok.Data
    public static class Data {
        List<Report> list;
    }

    @lombok.Data
    public static class Report {
        Long advertiser_id;
        String transaction_type;
        String create_time;
        BigDecimal grant;
        BigDecimal frozen;
        BigDecimal cash;
        BigDecimal dealbase;
        BigDecimal amount;
        BigDecimal return_goods;
        BigDecimal transaction_seq;
        BigDecimal remitter;
        BigDecimal payee;

    }
}
