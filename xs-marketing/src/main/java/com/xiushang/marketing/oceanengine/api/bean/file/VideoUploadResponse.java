package com.xiushang.marketing.oceanengine.api.bean.file;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;


@Data
public class VideoUploadResponse extends OceanEngineResponse<VideoUploadResponse.Data> {
    public static final VideoUploadResponse EMPTY = new VideoUploadResponse();
    @lombok.Data
    public static class Data {
        String video_id;
    }
}
