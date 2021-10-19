package com.xiushang.marketing.oceanengine.api.bean.ad;

import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class CampaignUpdateRequest extends BaseModel {
    private Long advertiser_id;
    private Long campaign_id;
    private String campaign_name;
    private String modify_time;
    private String budget_mode;
    private BigDecimal budget;
}
