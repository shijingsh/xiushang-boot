package com.xiushang.marketing.oceanengine.api.bean.convert;

import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
@Accessors(chain = true)
public class ConvertDetailRequest extends BaseModel {
    Long advertiser_id;
    Long convert_id;

    public String toQueryString() {
        return "advertiser_id=" + advertiser_id + "&convert_id=" + convert_id;
    }
}
