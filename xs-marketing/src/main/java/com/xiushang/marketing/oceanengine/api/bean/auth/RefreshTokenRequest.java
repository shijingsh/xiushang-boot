package com.xiushang.marketing.oceanengine.api.bean.auth;

import com.xiushang.marketing.oceanengine.api.bean.BaseModel;
import lombok.Data;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class RefreshTokenRequest extends BaseModel {
    private String app_id;
    private String secret;
    private String grant_type;
    private String refresh_token;

}
