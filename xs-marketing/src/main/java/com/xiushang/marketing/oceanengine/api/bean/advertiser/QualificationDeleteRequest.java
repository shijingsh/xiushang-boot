package com.xiushang.marketing.oceanengine.api.bean.advertiser;


import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import lombok.Data;


@Data
public class QualificationDeleteRequest extends BaseModel {
    Long advertiser_id;
    Long qualification_id;
}
