package com.xiushang.marketing.oceanengine.api.bean.dmp;

import lombok.Data;

import java.util.List;


@Data
public class AudienceDetailRequest {
    Long advertiser_id;
    List<Long> custom_audience_ids;
}
