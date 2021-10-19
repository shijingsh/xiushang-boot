package com.xiushang.marketing.oceanengine.api.bean.dmp;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;


@Data
public class DataSourceCreateResponse extends OceanEngineResponse<DataSourceCreateResponse.Data> {

    @lombok.Data
    public static class Data {
        String data_source_id;
    }
}
