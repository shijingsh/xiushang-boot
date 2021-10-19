package com.xiushang.marketing.oceanengine.api.bean.report;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.xiushang.marketing.oceanengine.support.utils.URIUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;


@Data
@Accessors(chain = true)
public class ReportCampaignRequest {
    Long advertiser_id;
    String start_date;
    String end_date;
    Integer page;
    Integer page_size;
    List<String> group_by;
    String time_granularity;
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
        List<Long> campaign_ids;
        String landing_type;
        String status = "CAMPAIGN_STATUS_ALL";
        String order_field;
        String order_type;
    }
}
