package com.xiushang.marketing.oceanengine.api.bean.ad;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import com.xiushang.marketing.oceanengine.support.Paging;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class CampaignGetResponse extends OceanEngineResponse<CampaignGetResponse.Data> {

    @lombok.Data
    public static class Data {
        List<CampaignInfo> list;
        Paging page_info;
    }

    @lombok.Data
    public static class CampaignInfo extends AdBase {
        Long id;
        Long advertiser_id;
        String name;
        BigDecimal budget;
        String budget_mode;
        String landing_type;
        String modify_time;
        String status;
        String campaign_create_time;
        String campaign_modify_time;
    }

}
