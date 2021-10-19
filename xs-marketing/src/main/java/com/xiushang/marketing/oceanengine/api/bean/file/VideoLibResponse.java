package com.xiushang.marketing.oceanengine.api.bean.file;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class VideoLibResponse extends OceanEngineResponse<ImageQueryResponse.Data> {

    @lombok.Data
    public static class Data {
        List<VideoInfo> list;
        PageInfo page_info;
    }

    @lombok.Data
    public static class VideoInfo {
        String id;
        Integer size;
        Integer width;
        Integer height;
        String url;
        String format;
        String signature;
        Integer poster_url;
        String bit_rate;
        Integer duration;
    }

    public static class PageInfo {
        Integer page;
        Integer total_page;
        Integer total_number;
    }
}
