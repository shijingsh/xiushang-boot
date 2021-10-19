package com.xiushang.marketing.oceanengine.api.bean.creative_word;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class WordQueryResponse extends OceanEngineResponse<WordQueryResponse.Data> {

    @lombok.Data
    public static class Data {
        List<Words> creative_word;
    }

    @lombok.Data
    public static class Words {
        Long creative_word_id;
        String name;
        String default_word;
        List<String> words;
        String content_type;
        Integer max_word_len;
        String status;
        Float user_rate;
    }
}
