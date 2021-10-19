package com.xiushang.marketing.oceanengine.api.bean.report;

import com.alibaba.fastjson.JSON;
import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import com.xiushang.marketing.oceanengine.support.utils.URIUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@Accessors(chain = true)
public class ReportAgentRequest extends BaseModel {
    Long advertiser_id;
    String start_date;
    String end_date;
    int page = 1;
    int page_size = 1000;
    List<String> group_by;
    String time_granularity;

    public String toQueryString() {
        StringBuilder builder = new StringBuilder();
        builder.append("advertiser_id=").append(advertiser_id);
        builder.append("&start_date=").append(start_date);
        builder.append("&end_date=").append(end_date);
        builder.append("&page=").append(page);
        builder.append("&page_size=").append(page_size);
        builder.append("&group_by=").append(URIUtil.encodeURIComponent(JSON.toJSONString(group_by)));
        builder.append("&time_granularity=").append(time_granularity);
        return builder.toString();
    }
}
