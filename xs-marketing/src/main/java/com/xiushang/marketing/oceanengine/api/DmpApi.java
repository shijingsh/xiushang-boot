package com.xiushang.marketing.oceanengine.api;

import com.xiushang.marketing.oceanengine.api.bean.dmp.*;
import com.xiushang.marketing.oceanengine.support.HttpMethod;
import com.xiushang.marketing.oceanengine.support.OceanEngineResource;
import com.xiushang.marketing.oceanengine.support.UrlConst;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
public class DmpApi extends OceanEngineResource {

    public static AudienceListResponse list(String token, AudienceListRequest request) {
        String url = UrlConst.DMP_AUDIENCE_LIST_URL + "?" + request.toQueryString();
        return execute(HttpMethod.GET, url, "", AudienceListResponse.class, token);
    }

    public static AudienceDeleteResponse delete(String token, AudienceDeleteRequest request) {
        String payload = request.toJSON();
        return execute(HttpMethod.POST, UrlConst.DMP_AUDIENCE_DELETE_URL, payload, AudienceDeleteResponse.class, token);
    }

    public static AudienceLookalikeResponse lookalike(String token, AudienceLookalikeRequest request) {
        String payload = request.toJSON();
        return execute(HttpMethod.POST, UrlConst.DMP_AUDIENCE_LOOKALIKE_URL, payload, AudienceLookalikeResponse.class, token);
    }

    public static AudienceCalcResponse calc(String token, AudienceCalcRequest request) {
        String payload = request.toJSON();
        return execute(HttpMethod.POST, UrlConst.DMP_AUDIENCE_CALC_URL, payload, AudienceCalcResponse.class, token);
    }

    public static DataSourceUploadResponse uploadFile(String token, DataSourceUploadRequest request) throws Exception {
        // TODO
        return null;
    }

    public static DataSourceCreateResponse createDatasource(String token, DataSourceCreateRequest request) {
        String payload = request.toJSON();
        return execute(HttpMethod.POST, UrlConst.DMP_DATASOURCE_CREATE_URL, payload, DataSourceCreateResponse.class, token);
    }

    public static DataSourceDetailResponse readDatasource(String token, DataSourceDetailRequest request) {
        String url = UrlConst.DMP_DATASOURCE_READ_URL + "?" + request.queryString();
        return execute(HttpMethod.GET, url, "", DataSourceDetailResponse.class, token);
    }

    public static AudiencePushResponse push(String token, AudiencePushRequest request) {
        String payload = request.toJSON();
        return execute(HttpMethod.POST, UrlConst.DMP_AUDIENCE_PUSH_URL, payload, AudiencePushResponse.class, token);
    }

    public static AudiencePublishResponse publish(String token, AudiencePublishRequest request) {
        String payload = request.toJSON();
        return execute(HttpMethod.POST, UrlConst.DMP_AUDIENCE_PUBLISH_URL, payload, AudiencePublishResponse.class, token);
    }

    public static AudienceReadResponse audienceRead(String token, AudienceReadRequest request) {
        String url = UrlConst.DMP_AUDIENCE_READ_URL + "?" + request.toQueryString();
        return execute(HttpMethod.GET, url, "", AudienceReadResponse.class, token);
    }


}
