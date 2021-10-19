package com.xiushang.marketing.oceanengine.api;

import com.xiushang.marketing.oceanengine.api.bean.cashflow.*;
import com.xiushang.marketing.oceanengine.support.HttpMethod;
import com.xiushang.marketing.oceanengine.support.OceanEngineResource;
import com.xiushang.marketing.oceanengine.support.OceanEngineRestException;
import com.xiushang.marketing.oceanengine.support.UrlConst;


public class CashFlowApi extends OceanEngineResource {

    public static CashResponse transferAdver(String token, CashRequest request) throws OceanEngineRestException {
        String payload = request.toJSON();
        return execute(HttpMethod.POST, UrlConst.AGENT_TRANSFER_URL, payload, CashResponse.class, token);
    }

    public static CashResponse refundAdver(String token, CashRequest request) throws OceanEngineRestException {
        String payload = request.toJSON();
        return execute(HttpMethod.POST, UrlConst.AGENT_REFUND_URL, payload, CashResponse.class, token);
    }

    public static AdverBalanceQueryResponse adverBalanceGet(String token, Long advertiserId) throws OceanEngineRestException {
        String url = String.format(UrlConst.ADVER_FOUND_BALANCE_URL, advertiserId);
        return execute(HttpMethod.GET, url, "", AdverBalanceQueryResponse.class, token);
    }


    public static AdverFundDailyResponse fundDailyReport(String token, AdverFundDailyRequest request) throws OceanEngineRestException {
        String url = UrlConst.ADVER_FOUND_DAILY_REPORT_URL + request.toQueryString();
        return execute(HttpMethod.GET, url, "", AdverFundDailyResponse.class, token);
    }

    public static AdverFundDetailResponse fundTransaction(String token, AdverFundDetailRequest request) throws OceanEngineRestException {
        String url = UrlConst.ADVER_FOUND_TRANX_DETAIL_URL + request.toQueryString();
        return execute(HttpMethod.GET, url, "", AdverFundDetailResponse.class, token);
    }
}
