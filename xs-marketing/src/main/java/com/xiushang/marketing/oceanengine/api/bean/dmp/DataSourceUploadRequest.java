package com.xiushang.marketing.oceanengine.api.bean.dmp;

import lombok.Data;

import java.io.File;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class DataSourceUploadRequest {
    Long advertiser_id;
    File file;
    String file_signature;
}
