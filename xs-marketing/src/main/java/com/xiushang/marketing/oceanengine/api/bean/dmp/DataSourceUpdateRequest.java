package com.xiushang.marketing.oceanengine.api.bean.dmp;

import lombok.Data;

import java.util.List;


@Data
public class DataSourceUpdateRequest {
    Long advertiser_id;
    String data_source_name;
    String description;
    Integer data_format;
    Integer file_storage_type;
    List<String> file_paths;
}
