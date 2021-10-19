package com.xiushang.marketing.oceanengine.api;

import com.xiushang.marketing.oceanengine.api.bean.creative_word.*;
import com.xiushang.marketing.oceanengine.support.HttpMethod;
import com.xiushang.marketing.oceanengine.support.OceanEngineResource;
import com.xiushang.marketing.oceanengine.support.OceanEngineRestException;
import com.xiushang.marketing.oceanengine.support.UrlConst;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
public class CreativeWordApi extends OceanEngineResource {

    public static WordQueryResponse query(String token, WordQueryRequest request) throws OceanEngineRestException {
        String url = UrlConst.CREATIVE_WORD_SELECT_URL + "?" + request.toQueryString();
        return execute(HttpMethod.GET, url, "", WordQueryResponse.class, token);
    }

    public static WordCreateResponse create(String token, WordCreateRequest request) throws OceanEngineRestException {
        String payload = request.toJSON();
        return execute(HttpMethod.POST, UrlConst.CREATIVE_WORD_CREATE_URL, payload, WordCreateResponse.class, token);
    }

    public static WordDeleteResponse delete(String token, WordDeleteRequest request) throws OceanEngineRestException {
        String payload = request.toJSON();
        return execute(HttpMethod.POST, UrlConst.CREATIVE_WORD_DELETE_URL, payload, WordDeleteResponse.class, token);
    }

    public static WordUpdateResponse update(String token, WordUpdateRequest request) throws OceanEngineRestException {
        String payload = request.toJSON();
        return execute(HttpMethod.POST, UrlConst.CREATIVE_WORD_UPDATE_URL, payload, WordUpdateResponse.class, token);
    }


}
