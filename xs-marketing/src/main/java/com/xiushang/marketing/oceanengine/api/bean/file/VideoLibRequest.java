package com.xiushang.marketing.oceanengine.api.bean.file;

import com.alibaba.fastjson.JSON;
import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import com.xiushang.marketing.oceanengine.support.utils.URIUtil;
import lombok.Data;


@Data
public class VideoLibRequest extends BaseModel {
    Long advertiser_id;
    Filter filtering;

    public String toQueryString() {
        return "advertiser_id=" + advertiser_id + "&filtering=" + URIUtil.encodeURIComponent(JSON.toJSONString(filtering));
    }

    @Data
    public static class Filter {
        Integer width;
        Integer height;
        Float[] ratio;
        String[] video_ids;
        Integer page;
        Integer page_size;
    }
}
