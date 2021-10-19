package com.xiushang.marketing.oceanengine.api.bean.advertiser;


import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
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
