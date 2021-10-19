package com.xiushang.marketing.oceanengine.api.bean.file;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;


@Data
public class VideoCoverImgResponse extends OceanEngineResponse<VideoCoverImgResponse.Image> {

    @Data
    public static class Image {
        String url;
        Integer width;
        Integer height;
        String id;
    }
}
