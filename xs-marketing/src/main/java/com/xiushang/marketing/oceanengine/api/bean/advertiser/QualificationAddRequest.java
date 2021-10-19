package com.xiushang.marketing.oceanengine.api.bean.advertiser;


import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import lombok.Data;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class QualificationAddRequest extends BaseModel {

    Long advertiser_id;
    String qualification_type;
    Long qualification_id;

}
