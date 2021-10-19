package com.xiushang.marketing.oceanengine.api.bean.report;

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
@Accessors(chain = true)
public class ReportIntegratedRequest extends BaseModel {
    Long advertiser_id;
    String start_date;
    String end_date;
    List<String> group_by;
    List<String> fields;
    Filtering filtering;
    PostFiltering post_filtering;
    int page = 1;
    int page_size = 1000;

    public String toQueryString() {
        StringBuilder builder = new StringBuilder();
        builder.append("advertiser_id=").append(advertiser_id);
        builder.append("&start_date=").append(start_date);
        builder.append("&end_date=").append(end_date);
        builder.append("&page=").append(page);
        builder.append("&page_size=").append(page_size);
        builder.append("&group_by=").append(URIUtil.encodeURIComponent(JSON.toJSONString(group_by)));
//        builder.append("&order_field=").append(order_field);
//        builder.append("&order_type=").append(order_type);
        if (Objects.nonNull(filtering)) {
            builder.append("&filtering=").append(URIUtil.encodeURIComponent(JSON.toJSONString(filtering)));
        }
        if (Objects.nonNull(post_filtering)) {
            builder.append("&post_filtering=").append(URIUtil.encodeURIComponent(JSON.toJSONString(post_filtering)));
        }
        if (Objects.nonNull(fields) && fields.size() > 0) {
            builder.append("&fields=").append(URIUtil.encodeURIComponent(JSON.toJSONString(fields)));
        }
        return builder.toString();
    }

    @Data
    public static class Filtering {
        List<Long> campaign_id;
        List<Long> ad_id;
        String pricing;
        String landing_type;
    }

    @Data
    public static class PostFiltering {

    }
}
