package com.xiushang.marketing.oceanengine.api.bean.convert;

import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
@Accessors(chain = true)
public class ConvertQueryAvailableConvertIdRequest extends BaseModel {
    Long advertiser_id;
    String external_url;
    String package_name;
    String itunes_url;
    List<String> fields;

    public String toQueryString() {
        StringBuilder builder = new StringBuilder();
        builder.append("advertiser_id=").append(advertiser_id);
        if (Objects.nonNull(external_url)) {
            builder.append("&external_url=").append(external_url);
        }
        if (Objects.nonNull(package_name)) {
            builder.append("&package_name=").append(package_name);
        }
        if (Objects.nonNull(itunes_url)) {
            builder.append("&itunes_url=").append(itunes_url);
        }
        return builder.toString();
    }
}
