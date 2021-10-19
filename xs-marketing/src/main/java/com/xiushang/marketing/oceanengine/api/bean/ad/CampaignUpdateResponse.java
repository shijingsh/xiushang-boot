package com.xiushang.marketing.oceanengine.api.bean.ad;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;



@Data
public class CampaignUpdateResponse extends OceanEngineResponse<CampaignUpdateResponse.Data> {

    @lombok.Data
    public static class Data {
        Long campaign_id;
    }

}
