package com.xiushang.marketing.oceanengine.api.bean.creative_word;

import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import lombok.Data;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class WordDeleteRequest extends BaseModel {
    Long advertiser_id;
    Long creative_word_id;
}
