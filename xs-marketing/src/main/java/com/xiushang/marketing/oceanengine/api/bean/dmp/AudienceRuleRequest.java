package com.xiushang.marketing.oceanengine.api.bean.dmp;

import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class AudienceRuleRequest {
    Long advertiser_id;
    String tag;
    String name;
    String profile_type;
    List<Object> profiles;

    public static class Pda {
        String behavior_code;
        String days;
        String dpa_id;
    }

    public static class Motor {
        String brand_id;
    }

    public static class Education {
        String education_id;
    }

    public static class Travel {
        String travel_id;
    }
}
