package com.xiushang.marketing.oceanengine.api.bean.ad;

import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class AdBudgetRequest extends BaseModel {
    private Long advertiser_id;
    private Long ad_id;
    private Double budget;
    private List<AdBudget> data;

    @Data
    public static class AdBudget {
        Long ad_id;
        Double budget;
    }
}
