package com.xiushang.marketing.oceanengine.api.bean.dmp;

import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class AudienceDetailRequest {
    Long advertiser_id;
    List<Long> custom_audience_ids;
}
