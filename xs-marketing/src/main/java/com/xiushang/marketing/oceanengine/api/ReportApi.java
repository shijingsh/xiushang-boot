package com.xiushang.marketing.oceanengine.api;

import com.xiushang.marketing.oceanengine.api.bean.report.*;
import com.xiushang.marketing.oceanengine.support.HttpMethod;
import com.xiushang.marketing.oceanengine.support.OceanEngineResource;
import com.xiushang.marketing.oceanengine.support.OceanEngineRestException;
import com.xiushang.marketing.oceanengine.support.UrlConst;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
public class ReportApi extends OceanEngineResource {

    public static ReportAdverResponse getAdverReport(String token, ReportAdverRequest request) throws OceanEngineRestException {
        String url = UrlConst.REPORT_ADVER_URL + request.toQueryString();
        return execute(HttpMethod.GET, url, "", ReportAdverResponse.class, token);
    }


    public static ReportCampaignResponse getCampaignReport(String token, ReportCampaignRequest request) throws OceanEngineRestException {
        String url = UrlConst.REPORT_CAMPAIGN_URL + request.toQueryString();
        return execute(HttpMethod.GET, url, "", ReportCampaignResponse.class, token);
    }

    public static ReportAdResponse getAdReport(String token, ReportAdRequest request) throws OceanEngineRestException {
        String url = UrlConst.REPORT_GROUP_URL + request.toQueryString();
        return execute(HttpMethod.GET, url, "", ReportAdResponse.class, token);
    }

    public static ReportCreativeResponse getCreativeReport(String token, ReportCreativeRequest request) throws OceanEngineRestException {
        String url = UrlConst.REPORT_CRATIVE_URL + request.toQueryString();
        return execute(HttpMethod.GET, url, "", ReportCreativeResponse.class, token);
    }

    public static ReportAgentResponse queryAgentReport(String token, ReportAgentRequest reportRequest) {
        String queryString = reportRequest.toQueryString();
        String url = UrlConst.REPORT_AGENT_URL + "?" + queryString;
        return execute(HttpMethod.GET, url, "", ReportAgentResponse.class, token);
    }

    public static ReportIntegratedResponse queryIntegratedReport(String token, ReportIntegratedRequest request){
        String url = UrlConst.REPORT_INTEGRATED_URL + "?"  + request.toQueryString();
        return execute(HttpMethod.GET, url, "", ReportIntegratedResponse.class, token);
    }
}
