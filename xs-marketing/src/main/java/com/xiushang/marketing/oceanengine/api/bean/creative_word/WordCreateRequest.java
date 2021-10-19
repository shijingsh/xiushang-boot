package com.xiushang.marketing.oceanengine.api.bean.creative_word;

import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import lombok.Data;

import java.util.List;


@Data
public class WordCreateRequest extends BaseModel {
    Long advertiser_id;
    String name;
    String default_word;
    List<String> words;
}
