package com.xiushang.marketing.test.tencent;

import com.tencent.ads.ApiContextConfig;
import com.tencent.ads.TencentAds;
import com.tencent.ads.exception.TencentAdsResponseException;
import com.tencent.ads.exception.TencentAdsSDKException;
import com.tencent.ads.model.*;
import com.tencent.ads.model.FilteringStruct;
import com.xiushang.marketing.test.config.TencentConfig;

import java.util.Arrays;
import java.util.List;

public class GetPromotedObjects {
    public String ACCESS_TOKEN = TencentConfig.ACCESS_TOKEN;

    public Long accountId = TencentConfig.ACCOUNT_ID;
    /** TencentAds */
    public TencentAds tencentAds;


    public List<FilteringStruct> filtering = null;

    public Long page = null;

    public Long pageSize = null;

    public List<String> fields =
            Arrays.asList(
                    "promoted_object_name",
                    "promoted_object_id",
                    "promoted_object_type",
                    "promoted_object_spec",
                    "created_time",
                    "last_modified_time");

    public void init() {
        this.tencentAds = TencentAds.getInstance();
        this.tencentAds.init(
                new ApiContextConfig().accessToken(ACCESS_TOKEN).isDebug(true)); // debug==true 会打印请求详细信息
        //this.tencentAds.useSandbox(); // 默认使用沙箱环境，如果要请求线上，这里需要设为线上环境
        this.tencentAds.useProduction();
        this.buildParams();
    }

    public void buildParams() {}

    public PromotedObjectsGetResponseData getPromotedObjects() throws Exception {
        PromotedObjectsGetResponseData response =
                tencentAds
                        .promotedObjects()
                        .promotedObjectsGet(accountId, filtering, page, pageSize, fields);
        return response;
    }

    public static void main(String[] args) {
        try {
            GetPromotedObjects getPromotedObjects = new GetPromotedObjects();
            getPromotedObjects.init();
            PromotedObjectsGetResponseData response = getPromotedObjects.getPromotedObjects();
        } catch (TencentAdsResponseException e) {
            e.printStackTrace();
        } catch (TencentAdsSDKException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

