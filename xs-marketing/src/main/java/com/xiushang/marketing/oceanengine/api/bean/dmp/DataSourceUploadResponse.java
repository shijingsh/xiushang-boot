package com.xiushang.marketing.oceanengine.api.bean.dmp;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;


@Data
public class DataSourceUploadResponse extends OceanEngineResponse<DataSourceUploadResponse.Data> {
    @lombok.Data
    public static class Data {
        String file_path;
    }
}
