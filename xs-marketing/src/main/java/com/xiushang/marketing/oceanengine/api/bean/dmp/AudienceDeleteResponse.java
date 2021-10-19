package com.xiushang.marketing.oceanengine.api.bean.dmp;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;


@Data
public class AudienceDeleteResponse extends OceanEngineResponse<AudienceDeleteResponse.Data> {
    @lombok.Data
    public static class Data {
        Long custom_audience_id;
    }
}
