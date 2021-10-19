package com.xiushang.marketing.oceanengine.api.bean.ad;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

import java.util.List;


@Data
public class AdStatusResponse extends OceanEngineResponse<AdStatusResponse.Data> {

    @lombok.Data
    public static class Data {
        List<Long> ad_ids;
    }

}
