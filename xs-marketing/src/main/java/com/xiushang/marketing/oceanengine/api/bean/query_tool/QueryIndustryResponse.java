package com.xiushang.marketing.oceanengine.api.bean.query_tool;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

import java.util.List;


@Data
public class QueryIndustryResponse extends OceanEngineResponse<QueryIndustryResponse.Data> {

    @lombok.Data
    public static class Data {
        List<Industry> industry_list;
    }

    @lombok.Data
    public static class Industry {
        Long industry_id;
        String industry_name;
        Integer level;
        Long first_industry_id;
        String first_industry_name;
        Long second_industry_id;
        String second_industry_name;
    }
}
