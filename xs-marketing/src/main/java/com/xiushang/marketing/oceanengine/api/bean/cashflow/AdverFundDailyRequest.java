package com.xiushang.marketing.oceanengine.api.bean.cashflow;


import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;


@Data
public class AdverFundDailyRequest extends BaseModel {
    private Long advertiser_id;
    private String start_date;
    private String end_date;
    private Integer page = 1;
    private Integer page_size = 10;

    public String toQueryString() {
        StringBuilder queryStr = new StringBuilder();
        queryStr.append("?advertiser_id=").append(advertiser_id);
        if (StringUtils.isNotEmpty(start_date)) {
            queryStr.append("&start_date=").append(start_date);
        }
        if (StringUtils.isNotEmpty(end_date)) {
            queryStr.append("&end_date=").append(end_date);
        }
        if (Objects.nonNull(page)) {
            queryStr.append("&page=").append(page);
        }
        if (Objects.nonNull(page_size)) {
            queryStr.append("&page_size=").append(page_size);
        }

        return queryStr.toString();
    }
}
