package com.xiushang.marketing.oceanengine.api.bean.dmp;

import com.xiushang.marketing.oceanengine.api.bean.BaseModel;

import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class AudiencePushRequest extends BaseModel {
    // "广告主ID")
    Long advertiser_id;
    // "人群包ID")
    Long custom_audience_id;
    // "推送广告主ID列表")
    List<Long> target_advertiser_ids;
}
