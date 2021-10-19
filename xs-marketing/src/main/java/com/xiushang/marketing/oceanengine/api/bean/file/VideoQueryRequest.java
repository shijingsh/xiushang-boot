package com.xiushang.marketing.oceanengine.api.bean.file;

import com.alibaba.fastjson.JSON;
import com.xiushang.marketing.oceanengine.support.utils.URIUtil;
import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class VideoQueryRequest {
    Long advertiser_id;
    List<String> video_ids;

    public String toQueryString() {
        return "advertiser_id=" + advertiser_id + "&video_ids=" + URIUtil.encodeURIComponent(JSON.toJSONString(video_ids));
    }
}
