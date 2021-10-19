package com.xiushang.marketing.oceanengine.api.bean.ad;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class AdCreateUpdateBase extends BaseModel {
    Long advertiser_id;
    String delivery_range;
    String budget_mode;
    //  // "广告预算(最低预算100元)")
    Double budget;
    // // "广告投放起始时间，形式如：2017-01-01 00:00")
    String start_time;
    //  // "广告投放结束时间，形式如：2017-01-01 00:00")
    String end_time;
    //   // "广告出价")
    Double bid;
    // // "广告出价类型, 详见【附录-广告出价类型】")
    String pricing;
    // // "广告投放时间类型,允许值: SCHEDULE_FROM_NOW, SCHEDULE_START_END")
    String schedule_type;
    //  // "广告投放时段")
    String schedule_time;
    // // "广告投放速度类型 允许值: FLOW_CONTROL_MODE_FAST, FLOW_CONTROL_MODE_SMOOTH, FLOW_CONTROL_MODE_BALANCE")
    String flow_control_mode;
    //  // "应用直达链接")
    String open_url;
    // // "DOWNLOAD_URL（下载链接，默认），EXTERNAL_URL（落地页链接）")
    String download_type;
    //  // "广告落地页链接")
    String external_url;
    //    // "广告应用下载链接")
    String download_url;
    //  // "广告名称，长度为1-100个字符，其中1个中文字符算2位")
    String name;
    //   // "广告应用下载类型 允许值: APP_ANDROID, APP_IOS")
    String app_type;

    //  // "广告应用下载包名")
    //   @JsonProperty("package")
    @JSONField(name = "package")
    String pkg;
    //   // "过滤已转化用户类型字段")
    String hide_if_converted;
    //   // "过滤已安装")
    Integer hide_if_exists;
    //    // "ocpm广告转化出价")
    Double cpa_bid;
    //  // "转化id")
    Long convert_id;

    //受众相关参数
    // // "定向人群包类型")
    String retargeting_type;
    List<Long> retargeting_tags;
    //   // "定向人群包列表")
    List<Long> retargeting_tags_include;
    //  // "排除人群包列表")
    List<Long> retargeting_tags_exclude;
    //  // "受众性别")
    String gender;
    // // "受众年龄区间")
    List<String> age;
    //  // "受众最低android版本")
    String android_osv;
    //  // "受众最低ios版本")
    String ios_osv;
    // // "受众运营商")
    List<String> carrier;
    // // "受众网络类型")
    List<String> ac;
    // // "受众手机品牌")
    List<String> device_brand;
    //// "受众文章分类")
    List<String> article_category;
    //// "用户首次激活时间")
    List<String> activate_type;
    // // "受众平台")
    List<String> platform;
    //// "地域定向城市")
    List<Long> city;
    // // "地域类型")
    String district;
    // // "受众位置类型")
    String location_type;
    // // "兴趣分类")
    List<Long> ad_tag;
    //// "兴趣关键词")
    Integer interest_type;
    List<Long> interest_tags;
    //  // "APP行为定向")
    String app_behavior_target;
    // // "APP行为定向")
    List<Long> app_category;
    //// "APP行为定向,APP集合")
    List<Long> app_ids;
    // // "产品目录ID")
    Long product_platform_id;
    //// "H5地址参数")
    String external_url_params;
    // // "直达链接参数")
    String open_url_params;
    //// "是否自定义商品定向")
    Long dpa_local_audience;
    List<Map<String, Object>> include_custom_actions;
    List<Map<String, Object>> exclude_custom_actions;
}
