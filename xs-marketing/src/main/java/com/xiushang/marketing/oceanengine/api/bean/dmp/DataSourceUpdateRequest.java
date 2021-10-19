package com.xiushang.marketing.oceanengine.api.bean.dmp;

import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class DataSourceUpdateRequest {
    Long advertiser_id;
    String data_source_name;
    String description;
    Integer data_format;
    Integer file_storage_type;
    List<String> file_paths;
}
