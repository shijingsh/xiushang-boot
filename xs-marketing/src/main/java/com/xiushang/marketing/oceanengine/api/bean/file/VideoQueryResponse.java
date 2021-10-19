package com.xiushang.marketing.oceanengine.api.bean.file;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;


@Data
public class VideoQueryResponse extends OceanEngineResponse<VideoQueryResponse.Image> {

    @Data
    public static class Image {
        Integer size;
        Integer width;
        Integer height;
        String format;
        String url;
        String signature;
        String poster_url;
        Integer bit_rate;
        Integer duration;
        String id;
    }
}
