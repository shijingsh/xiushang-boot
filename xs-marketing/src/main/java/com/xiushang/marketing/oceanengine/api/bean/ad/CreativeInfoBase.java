package com.xiushang.marketing.oceanengine.api.bean.ad;

import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import lombok.Data;

import java.util.List;


@Data
public class CreativeInfoBase extends BaseModel {
    Long advertiser_id;
    Long ad_id;
    String track_url;
    String action_track_url;
    String video_play_driving_track_url;
    String video_play_effective_track_url;
    String video_play_track_url;
    String video_play_done_track_url;
    Integer is_comment_disable; //允许值: 0, 1
    Integer close_video_detail;
    String creative_display_mode;//CREATIVE_DISPLAY_MODE_CTR  优选(优先投放预估点击率高的创意素材)
    /**
     * 是否使用优选广告位，0表示不使用优选，1表示使用，使用优选广告位的时候默认忽略inventory_type字段。
     * <p>
     * 默认值: 0
     * <p>
     * 允许值: 0, 1
     */
    Long smart_inventory;

    /**
     * INVENTORY_FEED	头条信息流
     * INVENTORY_TEXT_LINK	头条文章详情页
     * INVENTORY_VIDEO_FEED	西瓜信息流
     * INVENTORY_HOTSOON_FEED	火山信息流
     * INVENTORY_AWEME_FEED	抖音信息流
     * INVENTORY_UNION_SLOT	穿山甲
     */
    List<String> inventory_type;
    String source;
    String app_name;
    String web_url;
    List<String> ad_keywords;
    Long ad_category;
    List<Long> ad_categorys;
    String advanced_creative_type;
    String advanced_creative_title;
    String phone_number;
    String button_text;
    String form_url;
    String creative_material_mode;
    List<TitleList> title_list;
    List<ImageList> image_list;
    List<Creative> creatives;
    String landing_type;

    @Data
    public static class TitleList {
        String title;
        List<String> creative_word_ids;
    }

    @Data
    public static class ImageList {
        String image_mode;
        String image_id;
        String image_url;
        String video_id;
        String video_url;
        List<String> image_ids;
        List<String> image_urls;
    }

    @Data
    public static class Creative {
        Long creative_id;
        String title;
        List<String> creative_word_ids;
        String image_mode;
        String third_party_id;
        // image
        List<String> image_ids;
        List<String> image_urls;
        // video
        String image_id;
        String image_url;
        String video_id;
        String video_url;
    }

}
