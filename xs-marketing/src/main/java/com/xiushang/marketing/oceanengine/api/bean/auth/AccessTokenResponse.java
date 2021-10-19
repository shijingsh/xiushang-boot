package com.xiushang.marketing.oceanengine.api.bean.auth;


import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class AccessTokenResponse extends OceanEngineResponse<AccessTokenResponse.Data> {

    @lombok.Data
    public static class Data {
        private String access_token;
        private Integer expires_in;
        private String refresh_token;
        private Long advertiser_id;
        private Integer refresh_token_expires_in;
        private List<Long> advertiser_ids;
    }
}
