package com.xiushang.marketing.oceanengine.api.bean.advertiser;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class AdverMajordomoResponse extends OceanEngineResponse<List<AdverMajordomoResponse.Data>> {

    @lombok.Data
    public static class Data {
        List<AdverInfo> list;
    }

    @lombok.Data
    public static class AdverInfo {
        private Long advertiser_id;
        private String advertiser_name;
    }
}
