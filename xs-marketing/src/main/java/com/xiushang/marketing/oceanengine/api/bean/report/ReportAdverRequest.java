package com.xiushang.marketing.oceanengine.api.bean.report;

import com.google.common.base.Preconditions;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
@Accessors(chain = true)
public class ReportAdverRequest {
    Long advertiser_id;
    String start_date;
    String end_date;
    Integer page;
    Integer page_size;
    String time_granularity;

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
        builder.append("&time_granularity=")
                .append(Objects.isNull(time_granularity) ? "STAT_TIME_GRANULARITY_DAILY" : time_granularity);
        return builder.toString();
    }
}
