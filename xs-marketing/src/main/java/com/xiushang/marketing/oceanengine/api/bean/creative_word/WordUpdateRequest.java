package com.xiushang.marketing.oceanengine.api.bean.creative_word;

import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class WordUpdateRequest extends BaseModel {
    Long advertiser_id;
    Long creative_word_id;
    String name;
    String default_word;
    List<String> words;
}
