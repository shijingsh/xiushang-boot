package com.xiushang.marketing.oceanengine.api.bean.file;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class ImageUploadResponse extends OceanEngineResponse<ImageUploadResponse.Image> {
    public final static ImageUploadResponse EMPTY = new ImageUploadResponse();

    @Data
    public static class Image {
        Integer size;
        Integer width;
        Integer height;
        String format;
        String url;
        String signature;
        String id;
    }
}
