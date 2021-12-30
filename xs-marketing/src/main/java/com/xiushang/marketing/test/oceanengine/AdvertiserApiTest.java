package com.xiushang.marketing.test.oceanengine;

import com.alibaba.fastjson.JSON;
import com.xiushang.marketing.oceanengine.api.AdvertiserApi;
import com.xiushang.marketing.oceanengine.api.bean.advertiser.AdvertiserInfoResponse;
import com.xiushang.marketing.test.config.OceanEngineTestConfig;

import java.util.Arrays;
import java.util.List;

public class AdvertiserApiTest {

    public static void main(String[] args) {
        try {


            /**/
            List<Long> advertiserIds = Arrays.asList(4020251119595869l);
            List<String> fieldList = Arrays.asList("id", "name", "role", "status", "address", "reason", "license_url", "license_no", "license_province", "license_city", "company", "brand", "promotion_area", "promotion_center_province", "promotion_center_city", "industry", "create_time");
            AdvertiserInfoResponse response = AdvertiserApi.getInfo(OceanEngineTestConfig.ACCESS_TOKEN,advertiserIds,fieldList);
            System.out.println("=============================getInfo=============================");
            System.out.println(JSON.toJSONString(response));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
