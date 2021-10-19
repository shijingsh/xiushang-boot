package com.xiushang.marketing.oceanengine.api.bean.query_tool;

import com.alibaba.fastjson.JSON;
import com.xiushang.marketing.oceanengine.support.utils.URIUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
@Accessors(chain = true)
public class QueryPredictTaRequest {

    Long adverid;
    String retargeting_type;
    List<Long> retargeting_tags;
    String gender;
    List<String> age;
    String ios_osv;
    List<String> carrier;
    List<String> ac;
    List<String> device_brand;
    List<String> activate_type;
    List<String> platform;
    List<Long> city;
    String app_behavior_target;
    List<Long> app_category;
    List<Long> app_ids;


    public String toQueryString() {
        StringBuilder builder = new StringBuilder();
        builder.append("1=").append(1);
        if (Objects.nonNull(retargeting_type)) {
            builder.append("&retargeting_type=").append(retargeting_type);
        }
        if (Objects.nonNull(retargeting_tags)) {
            builder.append("&retargeting_tags=").append(URIUtil.encodeURIComponent(JSON.toJSONString(retargeting_tags)));
        }
        if (Objects.nonNull(gender)) {
            builder.append("&gender=").append(gender);
        }
        if (Objects.nonNull(age)) {
            builder.append("&age=").append(URIUtil.encodeURIComponent(JSON.toJSONString(age)));
        }
        if (Objects.nonNull(ios_osv)) {
            builder.append("&ios_osv=").append(ios_osv);
        }
        if (Objects.nonNull(carrier)) {
            builder.append("&carrier=").append(URIUtil.encodeURIComponent(JSON.toJSONString(carrier)));
        }
        if (Objects.nonNull(ac)) {
            builder.append("&ac=").append(URIUtil.encodeURIComponent(JSON.toJSONString(ac)));
        }
        if (Objects.nonNull(device_brand)) {
            builder.append("&device_brand=").append(URIUtil.encodeURIComponent(JSON.toJSONString(device_brand)));
        }
        if (Objects.nonNull(activate_type)) {
            builder.append("&activate_type=").append(URIUtil.encodeURIComponent(JSON.toJSONString(activate_type)));
        }
        if (Objects.nonNull(platform)) {
            builder.append("&platform=").append(URIUtil.encodeURIComponent(JSON.toJSONString(platform)));
        }

        if (Objects.nonNull(city)) {
            builder.append("&city=").append(URIUtil.encodeURIComponent(JSON.toJSONString(city)));
        }
        if (Objects.nonNull(app_behavior_target)) {
            builder.append("&app_behavior_target=").append(app_behavior_target);
        }
        if (Objects.nonNull(app_category)) {
            builder.append("&app_category=").append(app_category);
        }
        if (Objects.nonNull(app_ids)) {
            builder.append("&app_ids=").append(app_ids);
        }
        return builder.toString();
    }

}
