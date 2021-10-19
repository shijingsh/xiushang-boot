package com.xiushang.marketing.oceanengine.api.bean.dmp;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

import java.util.List;


@Data
public class DataSourceDetailResponse extends OceanEngineResponse<DataSourceDetailResponse.Data> {

    @lombok.Data
    public static class Data {
        List<Datasource> data_list;
    }

    @lombok.Data
    public static class Datasource {
        String data_source_id;
        String name;
        String description;
        Integer status;
        Long cover_num;
        Long upload_num;
        String create_time;
        String modify_time;
        List<ChangeLogs> change_logs;
        DefaultAudience default_audience;

    }

    @lombok.Data
    public static class ChangeLogs {
        Long change_log_id;
        String action;
        String file_storage_type;
        List<String> file_paths;
        String data_format;
        Integer status;
        String message;
        String create_time;
        String modify_time;
    }

    @lombok.Data
    public static class DefaultAudience {
        String custom_audience_id;
        String name;
        String tag;
        String upload_num;
        String cover_num;
        String source;
        String custom_type;
        String audience_generation_rule;
        String isdel;
    }
}
