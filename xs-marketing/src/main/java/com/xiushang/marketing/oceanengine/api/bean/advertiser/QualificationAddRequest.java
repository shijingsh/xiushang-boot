package com.xiushang.marketing.oceanengine.api.bean.advertiser;


import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import lombok.Data;


@Data
public class QualificationAddRequest extends BaseModel {

    Long advertiser_id;
    String qualification_type;
    Long qualification_id;

}
