package com.xiushang.marketing.oceanengine.api.bean.industry;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class GetIndustryResponse extends OceanEngineResponse<GetIndustryResponse.Data> {

    @lombok.Data
    public static class Data {
        List<Industry> industry_list;


    }

    @lombok.Data
    public static class Industry {
        Integer second_industry_id;
        String third_industry_name;
        Integer level;
        Integer industry_id;
        Integer first_industry_id;
        String second_industry_name;
        Integer third_industry_id;
        String first_industry_name;
        String industry_name;
    }
}
