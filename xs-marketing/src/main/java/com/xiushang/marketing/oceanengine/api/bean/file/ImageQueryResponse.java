package com.xiushang.marketing.oceanengine.api.bean.file;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class ImageQueryResponse extends OceanEngineResponse<ImageQueryResponse.Data> {

    @lombok.Data
    public static class Data {

    }

    @lombok.Data
    public static class Item {
        String id;
        Integer size;
        Integer width;
        Integer height;
        String url;
        String format;
        String signature;
    }
}
