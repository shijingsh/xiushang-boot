package com.xiushang.marketing.oceanengine.api.bean.ad;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class CreativeStatusResponse extends OceanEngineResponse<CreativeStatusResponse.Data> {

    @lombok.Data
    public static class Data {
        List<Long> creative_ids;
    }

}
