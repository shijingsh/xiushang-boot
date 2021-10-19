package com.xiushang.marketing.oceanengine.api.bean.file;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineRequest;
import lombok.Data;

import java.io.File;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class ImageUploadRequest  extends OceanEngineRequest<ImageUploadRequest> {
    Long advertiser_id;
    String upload_type;
    String image_signature;
    File image_file;
    String image_url;
}
