package com.xiushang.marketing.oceanengine.api.bean.creative_word;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import com.xiushang.marketing.oceanengine.api.bean.file.ImageQueryResponse;
import lombok.Data;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class WordCreateResponse extends OceanEngineResponse<ImageQueryResponse.Data> {

    @lombok.Data
    public static class Data {
        String creative_word_id;
    }
}
