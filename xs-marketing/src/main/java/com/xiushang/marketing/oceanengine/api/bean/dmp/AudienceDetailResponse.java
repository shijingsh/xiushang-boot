package com.xiushang.marketing.oceanengine.api.bean.dmp;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class AudienceDetailResponse extends OceanEngineResponse<AudienceDetailResponse.Data> {

    @lombok.Data
    public static class Data {
        List<Audience> custom_audience_list;
    }

    @lombok.Data
    public static class Audience {
        Long custom_audience_id;
        String data_source_id;
        String isdel;
        String name;
        String source;
        String status;
        String tag;
        String upload_num;
        String cover_num;
        Integer create_time;
        Integer modify_time;

    }
}
