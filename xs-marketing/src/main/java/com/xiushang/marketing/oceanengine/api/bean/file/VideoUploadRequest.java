package com.xiushang.marketing.oceanengine.api.bean.file;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineRequest;
import lombok.Data;

import java.io.File;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class VideoUploadRequest extends OceanEngineRequest<VideoUploadRequest> {
    Long advertiser_id;
    String video_signature;
    File video_file;
}
