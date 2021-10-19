package com.xiushang.marketing.oceanengine.api.bean.query_tool;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class QueryWebsiteResponse extends OceanEngineResponse<QueryWebsiteResponse.Data> {

    @lombok.Data
    public static class Data {
        List<WebsiteInfo> list;
        PageInfo page_info;
    }

    @lombok.Data
    public static class WebsiteInfo {
        String siteId;
        String name;
        String status;
        String siteType;
        String thumbnail;
    }

    @lombok.Data
    public static class PageInfo {
        Integer page_size;
        Integer page;
        Integer total_number;
        Integer total_page;
    }
}
