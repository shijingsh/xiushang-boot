package com.xiushang.marketing.oceanengine.api.bean.ad;

import com.alibaba.fastjson.JSON;
import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import com.xiushang.marketing.oceanengine.support.utils.URIUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class AdGetRequest extends BaseModel {
    private Long advertiser_id;
    private Integer page = 1;
    private Integer page_size = 1000;
    private Filter filtering;

    public String toQueryString() {
        StringBuilder builder = new StringBuilder();
        builder.append("?advertiser_id=").append(advertiser_id);
        builder.append("&page=").append(page);
        builder.append("&page_size=").append(page_size);
        if (!Objects.isNull(filtering))
            builder.append("&filtering=").append(URIUtil.encodeURIComponent(JSON.toJSONString(filtering)));
        return builder.toString();
    }

    @Data
    @Accessors(chain = true)
    public static class Filter {
        private List<Long> ids;
        private List<String> pricing_list;
        String status = "AD_STATUS_ALL";
        private Long campaign_id;
        private List<String> fields;
        private String ad_create_time;
        private String ad_modify_time;
    }
}
