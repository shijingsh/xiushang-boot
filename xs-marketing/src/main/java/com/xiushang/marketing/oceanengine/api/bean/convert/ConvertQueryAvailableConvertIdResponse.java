package com.xiushang.marketing.oceanengine.api.bean.convert;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@lombok.Data
@Accessors(chain = true)
public class ConvertQueryAvailableConvertIdResponse extends OceanEngineResponse<ConvertQueryAvailableConvertIdResponse.Data> {

    @lombok.Data
    public static class Data {
        List<ActiveConvert> active_convert_list;
    }

    @lombok.Data
    public static class ActiveConvert {
        Long id;
        String name;
        String convert_source_type;
        String convert_type;
        String action_track_url;
    }

}
