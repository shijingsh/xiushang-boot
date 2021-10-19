package com.xiushang.marketing.oceanengine.api.bean.ad;

import lombok.Data;


@Data
public class AdCreateRequest extends AdCreateUpdateBase {
    private Long campaign_id;
}
