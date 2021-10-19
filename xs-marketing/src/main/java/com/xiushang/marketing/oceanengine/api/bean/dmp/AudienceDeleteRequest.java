package com.xiushang.marketing.oceanengine.api.bean.dmp;

import com.xiushang.marketing.oceanengine.api.bean.BaseModel;

import lombok.Data;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class AudienceDeleteRequest extends BaseModel {
    String id;
    // "广告主ID")
    Long advertiser_id;
    // "人群包ID")
    Long custom_audience_id;
}
