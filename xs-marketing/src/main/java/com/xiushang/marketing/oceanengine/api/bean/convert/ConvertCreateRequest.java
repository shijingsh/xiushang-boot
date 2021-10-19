package com.xiushang.marketing.oceanengine.api.bean.convert;

import com.xiushang.marketing.oceanengine.api.bean.BaseModel;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class ConvertCreateRequest extends BaseModel {
    // "广告主ID")
    private Long advertiser_id;
    // "转化名称")
    private String name;
    // "转化来源", required = false)
    private String convert_source_type;
    // "下载链接", required = false)
    private String download_url;
    // "应用类型,允许值: APP_ANDROID, APP_IOS", required = false)
    private String app_type;
    // "转化监测链接", required = false)
    private String action_track_url;
    // "包名", required = false)
    private String package_name;
    // "转化类型，详见【附录-转化类型】", required = false)
    private String convert_type;
    private String convert_xpath_value;
    private String convert_xpath_url;
    // "落地页地址", required = false)
    private String external_url;
    private String app_id;
    // "直达链接", required = false)
    private String open_url;
    private String deep_external_action;
    // "转化跟踪方式 取值：1=落地页API，2=应用下载API", required = false)
    private String trackType;
}
