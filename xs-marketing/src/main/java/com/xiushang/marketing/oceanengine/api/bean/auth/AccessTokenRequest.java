package com.xiushang.marketing.oceanengine.api.bean.auth;


import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import lombok.Data;


@Data
public class AccessTokenRequest extends BaseModel {
    private String app_id;
    private String secret;
    private String grant_type;
    private String auth_code;
}
