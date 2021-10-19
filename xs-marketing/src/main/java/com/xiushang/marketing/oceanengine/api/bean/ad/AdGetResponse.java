package com.xiushang.marketing.oceanengine.api.bean.ad;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import com.xiushang.marketing.oceanengine.support.Paging;
import lombok.Data;

import java.util.List;
import java.util.Map;


@Data
public class AdGetResponse extends OceanEngineResponse<AdGetResponse.Data> {

    @lombok.Data
    public static class Data {
        List<AdInfo> list;
    }

    @lombok.Data
    public static class AdInfo extends AdBase {
        Long id;
        String name;
        Long advertiser_id;
        Long campaign_id;
        String modify_time;
        String delivery_range;
        String ad_modify_time;
        String ad_create_time;
        String budget_mode;
        Double budget;
        String status;
        String opt_status;
        String start_time;
        String end_time;
        Double bid;
        String pricing;
        String schedule_type;
        String schedule_time;
        String flow_control_mode;
        String open_url;
        String download_type;
        String external_url;
        String download_url;
        String app_type;
        @JSONField(name = "package")
        String pkg;
        String audit_reject_reason;

        Double cpa_bid;
        Long cpa_skip_first_phrase;
        Long convert_id;
        String convert_name;      // 自加
        String hide_if_converted;
        Integer hide_if_exists;

        Audience audience;

        Paging page_info;

        Integer interest_type;
        String landing_type;
        String rule_status;
        String campaignName;

        /**
         * TODO 目前缺失 download_type 类型的约束，默认download_type=DOWNLOAD_URL,此时download_url, app_type均有值
         *
         * @return
         */
        public String getApp_type() {
            return app_type;
        }

        private String defaultAppType() {
            return "EMPTY";
        }
    }

    @lombok.Data
    public static class Audience {
        //受众相关参数
        String retargeting_type;
        List<Long> retargeting_tags;
        List<Long> retargeting_tags_include;
        List<Long> retargeting_tags_exclude;
        String gender;
        List<String> age;
        String android_osv;
        String ios_osv;
        List<String> ac;
        List<String> device_brand;
        List<String> article_category;
        List<String> activate_type;
        List<String> platform;
        List<String> carrier;
        List<Long> ad_tag;
        List<Long> interest_tags;
        List<Long> city;
        String district;
        String location_type;
        String app_behavior_target;
        List<Long> app_category;
        List<Long> app_ids;
        Long product_platform_id;
        List<String> dpa_ad_type;
        List<String> dpa_external_urls;
        List<String> dpa_open_urls;
        List<String> dpa_category_type;
        List<Long> dpa_categories;
        List<Long> dpa_products;
        List<Long> external_url_params;
        String open_url_params;
        Long dpa_local_audience;
        List<Map<String, Object>> include_custom_actions;
        List<Map<String, Object>> exclude_custom_actions;
        String inventory_type;
    }

}
