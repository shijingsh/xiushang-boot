package com.xiushang.marketing.oceanengine.api;

import com.alibaba.fastjson.JSON;
import com.xiushang.marketing.oceanengine.api.bean.OceanEngineEmptyResponse;
import com.xiushang.marketing.oceanengine.api.bean.advertiser.*;
import com.xiushang.marketing.oceanengine.api.bean.cashflow.AdverFundDailyResponse;
import com.xiushang.marketing.oceanengine.support.HttpMethod;
import com.xiushang.marketing.oceanengine.support.OceanEngineResource;
import com.xiushang.marketing.oceanengine.support.OceanEngineRestException;
import com.xiushang.marketing.oceanengine.support.UrlConst;
import com.xiushang.marketing.oceanengine.support.utils.URIUtil;

import java.util.List;
import java.util.Objects;

/**
 * 广告主相关
 *
 */
public class AdvertiserApi extends OceanEngineResource {

    public static AdvertiserInfoResponse getInfo(String token, List<Long> advertiserIds, List<String> fieldList)
            throws OceanEngineRestException {
        String adverIds = URIUtil.encodeURIComponent(JSON.toJSONString(advertiserIds));
        String fields = "";
        if (Objects.nonNull(fieldList)) {
            fields = URIUtil.encodeURIComponent(JSON.toJSONString(fieldList));
        }
        // ?advertiser_ids=%s&fields=%s
        String url = UrlConst.ADVER_INFO_QUERY_URL + "?advertiser_ids=" + adverIds;
        //String url = String.format(UrlConst.ADVER_INFO_QUERY_URL, adverIds, fields);
        return execute(HttpMethod.GET, url, "", AdvertiserInfoResponse.class, token);
    }

    public static QualificationQueryResponse getQualification(String token, Long adverId) throws OceanEngineRestException {
        String url = String.format(UrlConst.ADVER_QUALIFICATION_QUERY_URL, adverId);
        return execute(HttpMethod.GET, url, "", QualificationQueryResponse.class, token);
    }

    public static OceanEngineEmptyResponse addQualification(String token, QualificationAddRequest request)
            throws OceanEngineRestException {
        String payload = request.toJSON();
        return execute(HttpMethod.POST, UrlConst.ADVER_QUALIFICATION_CREATE_URL, payload, OceanEngineEmptyResponse.class,
                token);
    }

    public static OceanEngineEmptyResponse updateQualification(String token, QualificationUpdateRequest request)
            throws OceanEngineRestException {
        String payload = request.toJSON();
        return execute(HttpMethod.POST, UrlConst.ADVER_QUALIFICATION_UPDATE_URL, payload, OceanEngineEmptyResponse.class,
                token);
    }

    public static OceanEngineEmptyResponse deteleQualification(String token, QualificationDeleteRequest request)
            throws OceanEngineRestException {
        String payload = request.toJSON();
        return execute(HttpMethod.POST, UrlConst.ADVER_QUALIFICATION_DELETE_URL, payload, OceanEngineEmptyResponse.class,
                token);
    }

    public static AdverFundDailyResponse queryPublicInfo(String token, List<Long> adverIds)
            throws OceanEngineRestException {
        String advertiserIds = JSON.toJSONString(adverIds);
        String url = String.format(UrlConst.ADVER_PUBLIC_INFO_URL, advertiserIds);
        return execute(HttpMethod.GET, url, "", AdverFundDailyResponse.class, token);
    }


    public static OceanEngineEmptyResponse updateBudget(String token, UpdateBudgetRequest request) {
        String payload = request.toJSON();
        return execute(HttpMethod.POST, UrlConst.ADVERTISER_UPDATE_BUDGET_URL, payload, OceanEngineEmptyResponse.class, token);
    }

    public static AdverMajordomoResponse getAdverMajordomoList(String token, Long adverId) throws OceanEngineRestException {
        String url = String.format(UrlConst.ADVER_MAJORDOMO_INFO_URL, adverId);
        return execute(HttpMethod.GET, url, "", AdverMajordomoResponse.class, token);
    }

    public static AdverBudgetResponse queryAdverBudgetBatch(String token, List<Long> adverIds) throws OceanEngineRestException {
        String advertiserIds = JSON.toJSONString(adverIds);
        String url = String.format(UrlConst.ADVER_BUDGET_QUERY_URL, advertiserIds);
        return execute(HttpMethod.GET, url, "", AdverBudgetResponse.class, token);
    }
}
