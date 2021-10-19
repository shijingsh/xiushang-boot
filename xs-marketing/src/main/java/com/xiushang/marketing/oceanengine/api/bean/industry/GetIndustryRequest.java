package com.xiushang.marketing.oceanengine.api.bean.industry;

import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import lombok.Data;


@Data
public class GetIndustryRequest extends BaseModel {
    Integer level;

    public String toQueryString() {
        StringBuilder builder = new StringBuilder();
        builder.append("?level=").append(level);
        return builder.toString();
    }
}
