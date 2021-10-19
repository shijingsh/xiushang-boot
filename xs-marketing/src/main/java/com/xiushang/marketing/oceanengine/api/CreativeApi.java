package com.xiushang.marketing.oceanengine.api;

import com.xiushang.marketing.oceanengine.api.bean.ad.*;
import com.xiushang.marketing.oceanengine.support.HttpMethod;
import com.xiushang.marketing.oceanengine.support.OceanEngineResource;
import com.xiushang.marketing.oceanengine.support.OceanEngineRestException;
import com.xiushang.marketing.oceanengine.support.UrlConst;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
public class CreativeApi extends OceanEngineResource {

    public static CreativeListResponse getList(String token, CreativeListRequest request) throws OceanEngineRestException {
        String url = UrlConst.AD_CREATIVE_LIST_URL + request.toQueryString();
        return execute(HttpMethod.GET, url, "", CreativeListResponse.class, token);
    }

    public static CreativeGetResponse get(String token, CreativeGetRequest request) throws OceanEngineRestException {
        String url = UrlConst.AD_CREATIVE_GET_URL + request.toQueryString();
        return execute(HttpMethod.GET, url, "", CreativeGetResponse.class, token);
    }

    public static CreativeGetDetailResponse getDetail(String token, CreativeGetDetailRequest request) throws OceanEngineRestException {
        String url = UrlConst.AD_CREATIVE_GET_DETAIL_URL + request.toQueryString();
        return execute(HttpMethod.GET, url, "", CreativeGetDetailResponse.class, token);
    }


    public static CreativeCreateResponse create(String token, CreativeCreateRequest request) throws OceanEngineRestException {
        String payload = request.toJSON();
        return execute(HttpMethod.POST, UrlConst.AD_CREATIVE_CREATE_URL, payload, CreativeCreateResponse.class, token);
    }

    public static CreativeUpdateResponse update(String token, CreativeUpdateRequest request) throws OceanEngineRestException {
        String payload = request.toJSON();
        return execute(HttpMethod.POST, UrlConst.AD_CREATIVE_UPDATE_URL, payload, CreativeUpdateResponse.class, token);
    }

    public static CreativeStatusResponse updateStatus(String token, CreativeStatusRequest request) throws OceanEngineRestException {
        String payload = request.toJSON();
        return execute(HttpMethod.POST, UrlConst.AD_CREATIVE_STATUS_URL, payload, CreativeStatusResponse.class, token);
    }


}
