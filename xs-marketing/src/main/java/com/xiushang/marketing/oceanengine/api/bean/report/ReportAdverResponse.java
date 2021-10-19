package com.xiushang.marketing.oceanengine.api.bean.report;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class ReportAdverResponse extends OceanEngineResponse<ReportAdverResponse.Data> {

    @lombok.Data
    public static class Data {
        List<ReportAdvertiser> list;
    }

    @lombok.Data
    public static class ReportAdvertiser extends ReportBase {
        String _id;
        String name;
        String company;
        Long advertiser_id;
        Boolean hasData;
        Boolean fix;
    }
}
