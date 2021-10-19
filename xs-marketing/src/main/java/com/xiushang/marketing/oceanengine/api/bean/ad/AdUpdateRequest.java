package com.xiushang.marketing.oceanengine.api.bean.ad;

import lombok.Data;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class AdUpdateRequest extends AdCreateUpdateBase {
    Long ad_id;
    String modify_time;
}
