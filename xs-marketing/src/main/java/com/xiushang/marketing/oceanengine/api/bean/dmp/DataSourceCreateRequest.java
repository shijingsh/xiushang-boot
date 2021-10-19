package com.xiushang.marketing.oceanengine.api.bean.dmp;

import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import lombok.Data;

import java.util.List;


@Data
public class DataSourceCreateRequest extends BaseModel {
    Long advertiser_id;
    String data_source_name;
    String description;
    Integer data_format;
    Integer file_storage_type;
    List<String> file_paths;
}
