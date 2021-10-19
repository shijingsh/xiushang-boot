package com.xiushang.marketing.oceanengine.api.bean.ad;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class CreativeUpdateResponse extends OceanEngineResponse<CreativeUpdateResponse.Data> {

    @lombok.Data
    public static class Data extends CreativeGetDetailResponse.CreativeInfos {

    }
}
