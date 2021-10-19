package com.xiushang.marketing.oceanengine.api.bean.convert;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class ConvertCreateResponse extends OceanEngineResponse<ConvertCreateResponse.Data> {
    @lombok.Data
    public static class Data {
        private Long id;
        private String opt_status;
        private String status;
    }

}
