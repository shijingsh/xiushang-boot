package com.xiushang.marketing.oceanengine.api.bean.ad;

import com.alibaba.fastjson.JSON;
import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import com.xiushang.marketing.oceanengine.support.utils.URIUtil;
import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class CreativeListRequest extends BaseModel {
    private Long advertiser_id;
    private Long ad_id;
    private Integer offset = 0;
    private Integer limit = 100;
    private List<String> fields;

    public String toQueryString() {
        StringBuilder builder = new StringBuilder();
        builder.append("?advertiser_id=").append(advertiser_id);
        if (!Objects.isNull(ad_id)) builder.append("&ad_id=").append(ad_id);
        builder.append("&offset=").append(offset);
        builder.append("&limit=").append(limit);
        if (!Objects.isNull(fields))
            builder.append("&fields=").append(URIUtil.encodeURIComponent(JSON.toJSONString(fields)));
        return builder.toString();
    }
}
