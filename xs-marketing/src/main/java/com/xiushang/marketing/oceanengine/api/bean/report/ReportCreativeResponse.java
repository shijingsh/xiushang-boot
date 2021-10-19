package com.xiushang.marketing.oceanengine.api.bean.report;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

import java.util.List;


@Data
public class ReportCreativeResponse extends OceanEngineResponse<ReportCreativeResponse.Data> {

    @lombok.Data
    public static class Data {
        List<ReportCreative> list;
    }

    @lombok.Data
    public static class ReportCreative extends ReportBase {
        String _id;
        Long creative_id;
        List<String> inventory_type;
        Long creativeid;
        String title;
        Boolean fix;

    }
}
