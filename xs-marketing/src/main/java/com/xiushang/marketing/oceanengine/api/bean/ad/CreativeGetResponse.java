package com.xiushang.marketing.oceanengine.api.bean.ad;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

import java.util.List;


@Data
public class CreativeGetResponse extends OceanEngineResponse<CreativeGetResponse.Data> {

    @lombok.Data
    public static class Data {
        PageInfo page_info;
        List<CreativeInfo> list;
    }


    @lombok.Data
    public static class CreativeInfo extends AdBase {
        String _id;
        Long creative_id;
        Long ad_id;
        Long advertiser_id;
        String title;
        List<String> creative_word_ids;
        String status;
        String opt_status;
        String image_mode;
        List<String> image_ids;
        List<String> image_urls;
        String image_id;
        String image_url;
        String video_id;
        String video_url;
        String third_party_id;
        String audit_reject_reason;
        List<Material> metarials;
        Long campid;
        Long adid;
        Long creativeid;
        String landing_type;
        List<Long> ad_categorys;
        String pricing; //出价类型
        String adName;
        String campaignName;
        String app_type;
    }

    @lombok.Data
    public class Image {
        String id;
        String url;
    }

    @lombok.Data
    public class Video {
        String id;
        String url;
    }


    @lombok.Data
    public static class Material {
        String image_id;
        String image_url;
        String video_id;
        String video_url;
        String audit_reject_reason;
        String title;
    }

    @lombok.Data
    public static class PageInfo {
        Integer page_size;
        Integer page;
        Integer total_number;
        Integer total_page;
    }


}
