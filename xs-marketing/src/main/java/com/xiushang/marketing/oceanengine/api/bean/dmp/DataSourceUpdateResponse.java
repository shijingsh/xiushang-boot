package com.xiushang.marketing.oceanengine.api.bean.dmp;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class DataSourceUpdateResponse extends OceanEngineResponse<DataSourceUpdateResponse.Data> {

    @lombok.Data
    public static class Data {
        String data_source_id;
    }
}
