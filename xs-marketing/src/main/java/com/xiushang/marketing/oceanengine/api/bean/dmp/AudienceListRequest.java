package com.xiushang.marketing.oceanengine.api.bean.dmp;


import lombok.Data;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class AudienceListRequest {
    // "广告主ID")
    Long advertiser_id;
    // "查询类型, 0: 属于该广告主的人群包, 1: 该广告主可用的人群包.")
    Integer select_type;
    // "分页偏移")
    Integer offset = 0;
    // "返回数据量")
    Integer limit = 100;

    public String toQueryString() {
        return "advertiser_id=" + advertiser_id + "&select_type=" + select_type + "&offset=" + offset + "&limit=" + limit;
    }
}
