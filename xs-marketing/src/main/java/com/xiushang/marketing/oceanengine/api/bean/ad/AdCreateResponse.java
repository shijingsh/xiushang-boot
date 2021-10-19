package com.xiushang.marketing.oceanengine.api.bean.ad;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;


@Data
public class AdCreateResponse extends OceanEngineResponse<AdCreateResponse.Data> {

    @lombok.Data
    public static class Data {
        private Long ad_id;
    }

}
