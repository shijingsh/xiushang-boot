package com.xiushang.marketing.oceanengine.api.bean.auth;

import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import lombok.Data;


@Data
public class AuthAdvertiserRequest extends BaseModel {
    private String access_token;
    private String app_id;
    private String secret;
}
