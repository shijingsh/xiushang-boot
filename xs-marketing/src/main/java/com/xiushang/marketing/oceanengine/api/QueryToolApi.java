package com.xiushang.marketing.oceanengine.api;

import com.xiushang.marketing.oceanengine.api.bean.query_tool.*;
import com.xiushang.marketing.oceanengine.support.HttpMethod;
import com.xiushang.marketing.oceanengine.support.OceanEngineResource;
import com.xiushang.marketing.oceanengine.support.UrlConst;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
public class QueryToolApi extends OceanEngineResource {

    public static QueryPredictTaResponse estimateAudience(String token, QueryPredictTaRequest request) {
        String queryString = request.toQueryString();
        String url = UrlConst.TOOLS_ESTIMATE_AUDIENCE_URL + "?" + queryString;
        return execute(HttpMethod.GET, url, "", QueryPredictTaResponse.class, token);
    }


    public static QueryAdQualityResponse adQuality(String token, QueryAdQualityRequest request) {
        String queryString = request.toQueryString();
        String url = UrlConst.TOOLS_AD_QUALITY_URL + "?" + queryString;
        return execute(HttpMethod.GET, url, "", QueryAdQualityResponse.class, token);
    }


    public static QueryWebsiteResponse website(String token, QueryWebsiteRequest request) {
        String queryString = request.toQueryString();
        String url = UrlConst.TOOLS_WEBSITE_URL + "?" + queryString;
        return execute(HttpMethod.GET, url, "", QueryWebsiteResponse.class, token);
    }


    public static QueryIndustryResponse industry(String token, QueryIndustryRequest request) {
        String queryString = request.toQueryString();
        String url = UrlConst.TOOLS_INDUSTRY_URL + "?" + queryString;
        return execute(HttpMethod.GET, url, "", QueryIndustryResponse.class, token);
    }


}
