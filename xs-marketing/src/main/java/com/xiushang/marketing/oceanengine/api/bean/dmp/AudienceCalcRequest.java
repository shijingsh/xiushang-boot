package com.xiushang.marketing.oceanengine.api.bean.dmp;

import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import lombok.Data;

import java.util.List;


@Data
public class AudienceCalcRequest extends BaseModel {
    Long advertiser_id;
    Long custom_audience_id;
    String tag;
    String name;
    List<Long> union_ids;
    List<Long> intersection_ids;
    List<Long> exclusion_ids;

}
