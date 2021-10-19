package com.xiushang.marketing.oceanengine.api.bean.agent;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import com.xiushang.marketing.oceanengine.support.Paging;
import lombok.Data;

import java.util.List;


@Data
public class ListChildResponse extends OceanEngineResponse<ListChildResponse.Data> {
    Paging page_info;

    @lombok.Data
    public static class Data {
        List<Long> child_agent_ids;
    }
}
