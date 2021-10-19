package com.xiushang.marketing.oceanengine.api.bean.ad;

import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class CampaignStatusRequest extends BaseModel {
    private Long advertiser_id;
    private List<Long> campaign_ids;
    private String opt_status;
}
