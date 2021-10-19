package com.xiushang.marketing.oceanengine.api.bean.ad;

import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import lombok.Data;

import java.util.List;


@Data
public class AdBidPriceRequest extends BaseModel {
    private Long advertiser_id;
    private List<Long> ad_ids;
    private Double bid;
    private List<AdBudget> data;

    @Data
    public static class AdBudget {
        Long ad_id;
        Double bid;
    }
}
