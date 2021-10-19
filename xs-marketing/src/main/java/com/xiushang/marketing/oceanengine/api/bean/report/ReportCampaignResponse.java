package com.xiushang.marketing.oceanengine.api.bean.report;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class ReportCampaignResponse extends OceanEngineResponse<ReportCampaignResponse.Data> {

    @lombok.Data
    public static class Data {
        List<ReportCampaign> list;
    }

    @lombok.Data
    public static class ReportCampaign extends ReportBase {

        String _id;
        Long campaign_id;

        String landing_type;
        String status;
        Long campaignid;
        String name;
        Boolean fix;
    }
}
