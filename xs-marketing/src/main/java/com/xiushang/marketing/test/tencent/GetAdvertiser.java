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

public class GetAdvertiser {
    public String ACCESS_TOKEN = TencentConfig.ACCESS_TOKEN;

    public Long accountId = TencentConfig.ACCOUNT_ID;
    /** TencentAds */
    public TencentAds tencentAds;


    public List<FilteringStruct> filtering = null;

    public List<String> fields =
            Arrays.asList(
                    "account_id",
                    "daily_budget",
                    "system_status",
                    "reject_message",
                    "corporation_name",
                    "corporation_licence",
                    "certification_image_id",
                    "certification_image",
                    "identity_number",
                    "individual_qualification",
                    "corporate_image_name",
                    "corporate_image_logo",
                    "system_industry_id",
                    "customized_industry",
                    "introduction_url",
                    "industry_qualification_image_id_list",
                    "industry_qualification_image",
                    "ad_qualification_image_id_list",
                    "ad_qualification_image",
                    "contact_person",
                    "contact_person_email",
                    "contact_person_telephone",
                    "contact_person_mobile",
                    "wechat_spec",
                    "websites");

    public Long page = null;

    public Long pageSize = null;

    public void init() {
        this.tencentAds = TencentAds.getInstance();
        this.tencentAds.init(
                new ApiContextConfig().accessToken(ACCESS_TOKEN).isDebug(true)); // debug==true 会打印请求详细信息
        //this.tencentAds.useSandbox(); // 默认使用沙箱环境，如果要请求线上，这里需要设为线上环境
        this.tencentAds.useProduction();
        this.buildParams();
    }

    public void buildParams() {}

    public AdvertiserGetResponseData getAdvertiser() throws Exception {
        AdvertiserGetResponseData response =
                tencentAds.advertiser().advertiserGet(accountId, filtering, fields, page, pageSize);
        return response;
    }

    public static void main(String[] args) {
        try {
            GetAdvertiser getAdvertiser = new GetAdvertiser();
            getAdvertiser.init();
            AdvertiserGetResponseData response = getAdvertiser.getAdvertiser();
        } catch (TencentAdsResponseException e) {
            e.printStackTrace();
        } catch (TencentAdsSDKException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}