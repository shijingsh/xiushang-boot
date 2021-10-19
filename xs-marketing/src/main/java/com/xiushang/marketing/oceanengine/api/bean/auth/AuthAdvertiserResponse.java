package com.xiushang.marketing.oceanengine.api.bean.auth;

import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;
import lombok.Data;

import java.util.List;


@Data
public class AuthAdvertiserResponse extends OceanEngineResponse<AuthAdvertiserResponse.Data> {
    @lombok.Data
    public static class Data {
        List<AuthAdvertiserInfo> list;
    }

    @lombok.Data
    public static class AuthAdvertiserInfo {
        Long advertiser_id;
        String advertiserName;
        String account_role;
        Integer advertiser_role;
        Boolean is_valid;
    }
}
