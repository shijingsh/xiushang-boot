package com.xiushang.marketing.oceanengine.api.bean.dmp;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

import java.util.List;


@Data
public class AudienceReadResponse extends OceanEngineResponse<AudienceReadResponse.Data> {

    @lombok.Data
    public static class Data {
        List<AudienceDetailResponse.Audience> custom_audience_list;
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

    }
}
