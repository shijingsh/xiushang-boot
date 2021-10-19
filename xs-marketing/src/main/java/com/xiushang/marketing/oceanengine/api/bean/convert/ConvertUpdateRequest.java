package com.xiushang.marketing.oceanengine.api.bean.convert;

import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
@Accessors(chain = true)
public class ConvertUpdateRequest extends BaseModel {
    Long advertiser_id;
    Long convert_id;
    String download_url;
}
