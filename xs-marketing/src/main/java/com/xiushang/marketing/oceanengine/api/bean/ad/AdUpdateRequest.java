package com.xiushang.marketing.oceanengine.api.bean.ad;

import lombok.Data;


@Data
public class AdUpdateRequest extends AdCreateUpdateBase {
    Long ad_id;
    String modify_time;
}
