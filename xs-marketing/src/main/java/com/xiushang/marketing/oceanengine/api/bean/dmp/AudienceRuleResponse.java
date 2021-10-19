package com.xiushang.marketing.oceanengine.api.bean.dmp;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;


@Data
public class AudienceRuleResponse extends OceanEngineResponse<AudienceRuleResponse.Data> {
    @lombok.Data
    public static class Data {
        Long custom_audience_id;
    }
}
