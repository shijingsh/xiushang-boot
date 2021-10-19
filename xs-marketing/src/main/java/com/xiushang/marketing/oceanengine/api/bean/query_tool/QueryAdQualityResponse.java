package com.xiushang.marketing.oceanengine.api.bean.query_tool;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

import java.util.List;


@Data
public class QueryAdQualityResponse extends OceanEngineResponse<QueryAdQualityResponse.Data> {

    @lombok.Data
    public static class Data {
        List<Quality> list;
    }

    @lombok.Data
    public static class Quality {
        Long ad_id;
        Float quality_score;
        Float ctr_score;
        Float web_score;
        Float cvr_score;
    }
}
