package com.xiushang.marketing.oceanengine.api.bean.agent;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import com.xiushang.marketing.oceanengine.support.Paging;
import lombok.Data;


@Data
public class UpdateAdverResponse extends OceanEngineResponse<UpdateAdverResponse.Data> {
    Paging page_info;

    @lombok.Data
    public static class Data {
        Long advertiser_id;
        Long need_audit;
    }
}
