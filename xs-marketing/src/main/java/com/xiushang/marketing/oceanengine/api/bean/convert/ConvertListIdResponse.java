package com.xiushang.marketing.oceanengine.api.bean.convert;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.experimental.Accessors;

import java.util.List;


@lombok.Data
@Accessors(chain = true)
public class ConvertListIdResponse extends OceanEngineResponse<ConvertListIdResponse.Data> {

    @lombok.Data
    public static class Data {
        List<AdConvert> ad_convert_list;
    }

    @lombok.Data
    public static class AdConvert {
        Long id;
        String name;
        String opt_status;
        String convert_source_type;
        String status;
        String convert_type;
        String action_track_url;
        PageInfo page_info;
    }

    @lombok.Data
    public static class PageInfo {
        Integer current_page;
        Integer total_page;
    }

}
