package com.xiushang.marketing.oceanengine.api.bean.dmp;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;


@Data
public class DataSourceUpdateResponse extends OceanEngineResponse<DataSourceUpdateResponse.Data> {

    @lombok.Data
    public static class Data {
        String data_source_id;
    }
}
