package com.xiushang.marketing.oceanengine.api.bean.advertiser;


import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import lombok.Data;


@Data
public class QualificationUpdateRequest extends BaseModel {

    Long advertiser_id;
    String description;
    String license_file_id;
    String license_no;
    Long qualification_id;
    String qualification_img_id;

}
