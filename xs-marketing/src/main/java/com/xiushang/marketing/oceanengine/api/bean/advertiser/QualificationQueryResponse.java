package com.xiushang.marketing.oceanengine.api.bean.advertiser;


import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

import java.util.List;


@Data
public class QualificationQueryResponse extends OceanEngineResponse<QualificationQueryResponse.Data> {

    @lombok.Data
    public static class Data {
        List<Qualification> list;
    }

    @lombok.Data
    public static class Qualification {
        Long qualification_id;
        String qualification_type;
        String description;
    }
}
