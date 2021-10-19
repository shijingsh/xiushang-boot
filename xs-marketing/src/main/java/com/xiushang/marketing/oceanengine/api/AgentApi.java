package com.xiushang.marketing.oceanengine.api;

import com.xiushang.marketing.oceanengine.api.bean.agent.*;
import com.xiushang.marketing.oceanengine.support.HttpMethod;
import com.xiushang.marketing.oceanengine.support.OceanEngineResource;
import com.xiushang.marketing.oceanengine.support.OceanEngineRestException;
import com.xiushang.marketing.oceanengine.support.UrlConst;

import java.util.Objects;


public class AgentApi extends OceanEngineResource {

    public static ListAdverResponse listAdver(String token, Integer page, Integer pageSize) throws OceanEngineRestException {
        page = Objects.isNull(page) ? 1 : page;
        pageSize = Objects.isNull(pageSize) ? 10 : pageSize;
        String url = String.format(UrlConst.AGENT_ADVER_LIST_URL, page, pageSize);
        return execute(HttpMethod.GET, url, "", ListAdverResponse.class, token);
    }

    public static CreateAdverResponse createAdver(String token, CreateAdverRequest request) throws OceanEngineRestException {
        String payload = request.toJSON();
        return execute(HttpMethod.POST, UrlConst.AGENT_ADVER_CREATE_URL, payload, CreateAdverResponse.class, token);
    }

    public static UpdateAdverResponse updateAdver(String token, UpdateAdverRequest request) throws OceanEngineRestException {
        String payload = request.toJSON();
        return execute(HttpMethod.POST, UrlConst.AGENT_ADVER_UPDATE_URL, payload, UpdateAdverResponse.class, token);
    }

    public static ListChildResponse listChild(String token, Integer page, Integer pageSize) throws OceanEngineRestException {
        page = Objects.isNull(page) ? 1 : page;
        pageSize = Objects.isNull(pageSize) ? 10 : pageSize;
        String url = String.format(UrlConst.AGENT_CHILD_LIST_URL, page, pageSize);
        return execute(HttpMethod.GET, url, "", ListChildResponse.class, token);
    }


}
