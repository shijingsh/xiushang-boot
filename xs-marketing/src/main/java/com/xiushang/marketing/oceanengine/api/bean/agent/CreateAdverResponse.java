package com.xiushang.marketing.oceanengine.api.bean.agent;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;


@Data
public class CreateAdverResponse extends OceanEngineResponse<CreateAdverResponse.Data> {

    @lombok.Data
    public static class Data {
        Long advertiser_id;
    }
}
