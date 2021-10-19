package com.xiushang.marketing.oceanengine.api.bean.report;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class ReportIntegratedResponse extends OceanEngineResponse<ReportIntegratedResponse.Data> {
    @lombok.Data
    public static class Data {
        List<ReportData> list;
        PageInfo page_info;
    }

    @lombok.Data
    public static class ReportData {
        Metric metrics;
        Dimensions dimensions;
    }

    @lombok.Data
    public static class PageInfo {
        Integer page;
        Integer page_size;
        Integer total_page;
        Integer total_number;
    }

    @lombok.Data
    public static class Dimensions {
        String stat_datetime;
        Long advertiser_id;
        String campaign_name;
        Long campaigin_id;
        Long ad_id;
        String ad_name;
        String landing_type;
    }

    @lombok.Data
    public static class Metric {
        String stat_datetime;
        Long advertiser_id;
        Long show = 0L;
        Double avg_show_cost = 0.0;
        Double ctr = 0.0;
        Long click = 0L;
        Double avg_click_cost = 0.0;
        Long convert = 0L;
        Double convert_cost = 0.0;
        Double convert_rate = 0.0;
        Double cost = 0.0;
        Long active = 0L;
        Double active_cost = 0.0;
        Long download_finish = 0L;
        Double download_finish_cost = 0.0;
        Long download_start = 0L;
        Double download_start_cost = 0.0;
        Long install_finish = 0L;
        Long register = 0L;
        Double active_register_cost = 0.0;
        Long pay_count = 0L;
        Long in_app_uv = 0L;
        Long in_app_detail_uv = 0L;
        Long in_app_cart = 0L;
        Long in_app_order = 0L;
        Long in_app_pay = 0L;
        Long phone = 0L;
        Long form = 0L;
        Long map_search = 0L;
        Long button = 0L;
        Long view = 0L;
        Long qq = 0L;
        Long lottery = 0L;
        Long vote = 0L;
        Long redirect = 0L;
        Long shopping = 0L;
        Long consult = 0L;
        Long wechat = 0L;
        Long phone_confirm = 0L;
        Long phone_connect = 0L;
        Long consult_effective = 0L;
        Long total_play = 0L;
        Long valid_play = 0L;
        Long wifi_play = 0L;
        Long play_duration_sum = 0L;
        Long play_25_feed_break = 0L;
        Long play_50_feed_break = 0L;
        Long play_75_feed_break = 0L;
        Long play_100_feed_break = 0L;
        Long advanced_creative_phone_click = 0L;
        Long advanced_creative_counsel_click = 0L;
        Long advanced_creative_form_click = 0L;
        Long share = 0L;
        Long comment = 0L;
        Long like = 0L;
        Long follow = 0L;
        Long home_visited = 0L;
        Long ies_challenge_click = 0L;
        Long ies_music_click = 0L;
        Float interact_per_cost = 0F;
    }
}
