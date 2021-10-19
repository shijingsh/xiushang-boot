package com.xiushang.marketing.oceanengine.api.bean.convert;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.experimental.Accessors;


@lombok.Data
@Accessors(chain = true)
public class ConvertDetailResponse extends OceanEngineResponse<ConvertDetailResponse.ConvertDetail> {

    @lombok.Data
    public static class ConvertDetail {
        public static final ConvertDetail EMPTY = new ConvertDetail();
        Long id;
        String name;
        String app_type;
        String package_name;
        String download_url;
        String opt_status;
        String convert_source_type;
        String status;
        String convert_type;
        String action_track_url;
        String convert_activate_callback_url;
        String convert_secret_key;
        String app_id;
        String external_url;
        String convert_track_params;
        String convert_base_code;
        String convert_js_code;
        String convert_html_code;
        String convert_xpath_url;
        String convert_xpath_value;
        String open_url;
        String trackType;
    }

    @lombok.Data
    public static class H5Landingpage {
        Long id;
        String name;
        String convert_source_type;
        String status;
        String opt_status;
        String external_url;
        String action_track_url;
        String convert_track_params;
        String convert_type;
    }

    @lombok.Data
    public static class AppDownload {
        Long id;
        String name;
        String convert_source_type;
        String app_type;
        String download_url;
        String convert_type;
        String convert_activate_callback_url;
        String convert_secret_key;
        String action_track_url;
        String package_name;
        String deep_external_action;
        String status;
        String opt_status;

    }
}
