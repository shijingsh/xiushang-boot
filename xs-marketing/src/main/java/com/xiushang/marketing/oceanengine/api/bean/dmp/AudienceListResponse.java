package com.xiushang.marketing.oceanengine.api.bean.dmp;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;



import java.util.List;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class AudienceListResponse extends OceanEngineResponse<AudienceListResponse.Data> {

    @lombok.Data
    public static class Data {
        List<Audience> custom_audience_list;
    }

    @lombok.Data
    public static class Audience {

        String id;
        Long custom_audience_id;
        String isdel;
        String data_source_id;
        String name;
        String source;
        Integer status;
        Long cover_num;
        Long upload_num;
        String tag;
        Long create_time;
        Long modify_time;
        Integer push_status;

        Long adverid;
        Long agentid;
        String createTime;
        String updateTime;
        String ossKey;
        Integer deviceType;
        String remarks;
    }
}
