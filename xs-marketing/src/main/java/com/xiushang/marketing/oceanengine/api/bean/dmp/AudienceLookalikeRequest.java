package com.xiushang.marketing.oceanengine.api.bean.dmp;

import com.xiushang.marketing.oceanengine.api.bean.BaseModel;

import lombok.Data;


@Data
public class AudienceLookalikeRequest extends BaseModel {
    // "广告主ID")
    Long advertiser_id;
    // "人群包ID")
    Long custom_audience_id;
    // "人群包标签，即人群分组")
    String tag;
    // "人群包名称")
    String name;
    // "扩展数量,取值范围: 1-10000000")
    Long lookalike_num;
    // "扩展设备，NONE为不限,允许值: NONE, IOS, ANDROID")
    String platform;

}
