package com.xiushang.marketing.oceanengine.api.bean.advertiser;


import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

import java.util.List;


@Data
public class AdverPublicInfoResponse extends OceanEngineResponse<List<AdverPublicInfoResponse.Data>> {

    @lombok.Data
    public static class Data {
        private Long id;
        private String name;
        private String company;
    }

}
