package com.xiushang.marketing.oceanengine.api.bean.file;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;


@Data
public class ImageLibResponse extends OceanEngineResponse<ImageQueryResponse.Data> {

    @lombok.Data
    public static class Data {
        String id;
        Integer size;
        Integer width;
        Integer height;
        String url;
        String format;
        String signature;
        Integer page;
        Integer page_size;
        Integer total_page;
        Integer total_number;
    }
}
