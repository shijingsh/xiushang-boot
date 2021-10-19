package com.xiushang.marketing.oceanengine.api.bean.ad;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

import java.util.List;


@Data
public class CreativeStatusResponse extends OceanEngineResponse<CreativeStatusResponse.Data> {

    @lombok.Data
    public static class Data {
        List<Long> creative_ids;
    }

}
