package com.xiushang.marketing.oceanengine.api.bean.agent;

import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import lombok.Data;


@Data
public class UpdateAdverRequest extends BaseModel {
    private Long advertiser_id;
    private String name;
    private String contacter;
    private String phonenumber;
    private String telephone;
}
