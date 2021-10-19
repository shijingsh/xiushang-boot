package com.xiushang.marketing.oceanengine.api.bean.agent;

import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import lombok.Data;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class UpdateAdverRequest extends BaseModel {
    private Long advertiser_id;
    private String name;
    private String contacter;
    private String phonenumber;
    private String telephone;
}
