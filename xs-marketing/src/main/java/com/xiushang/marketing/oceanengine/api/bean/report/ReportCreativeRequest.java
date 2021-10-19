package com.xiushang.marketing.oceanengine.api.bean.report;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.xiushang.marketing.oceanengine.support.code.TimeGranularity;
import com.xiushang.marketing.oceanengine.support.utils.URIUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;


@Data
@Accessors(chain = true)
public class ReportCreativeRequest {
    Long advertiser_id;
    String start_date;
    String end_date;
    Integer page;
    Integer page_size;
    List<String> group_by;
    TimeGranularity time_granularity;

    Filtering filtering;


    public String toQueryString() {
        Preconditions.checkArgument(Objects.nonNull(advertiser_id), "advertiser_id can not be null");
        Preconditions.checkArgument(Objects.nonNull(start_date), "start_date can not be null");
        Preconditions.checkArgument(Objects.nonNull(end_date), "end_date can not be null");
        StringBuilder builder = new StringBuilder();
        builder.append("?advertiser_id=").append(advertiser_id);
        builder.append("&start_date=").append(start_date);
        builder.append("&end_date=").append(end_date);
        builder.append("&page=").append(Objects.isNull(page) ? 1 : page);
        builder.append("&page_size=").append(Objects.isNull(page_size) ? 10 : page_size);
        builder.append("&time_granularity=").append(Objects.isNull(time_granularity) ? "STAT_TIME_GRANULARITY_DAILY" : time_granularity);
        builder.append("&group_by=").append(URIUtil.encodeURIComponent(Objects.isNull(group_by) ? "" : JSON.toJSONString(group_by)));
        if (Objects.nonNull(filtering)) {
            builder.append("&filtering=").append(URIUtil.encodeURIComponent(JSON.toJSONString(filtering)));
        }
        return builder.toString();
    }


    @Data
    public static class Filtering {
        Long campaign_id;
        Long ad_ids;
        List<Long> creative_ids;
        String pricing;
        String status = "CREATIVE_STATUS_ALL";
        String image_mode;
        String landing_type;
        String order_field;
        String order_type;
    }
}
