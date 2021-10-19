package com.xiushang.marketing.oceanengine.api.bean.file;

import lombok.Data;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class VideoCoverImgRequest {
    Long advertiser_id;
    String video_id;

    public String toQueryString() {
        return "advertiser_id=" + advertiser_id + "&video_id=" + video_id;
    }
}
