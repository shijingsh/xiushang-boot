package com.xiushang.marketing.oceanengine.api.bean.query_tool;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class QueryWebsiteRequest {
    Long advertiser_id;
    Integer page = 1;
    Integer page_size = 100;

    public String toQueryString() {
        return "advertiser_id=" + advertiser_id + "&page=" + page + "&page_size=" + page_size;
    }
}
