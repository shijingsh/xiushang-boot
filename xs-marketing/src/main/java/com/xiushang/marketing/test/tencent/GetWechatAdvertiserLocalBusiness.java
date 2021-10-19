package com.xiushang.ads.test.tencent;

import com.tencent.ads.ApiContextConfig;
import com.tencent.ads.TencentAds;
import com.tencent.ads.exception.TencentAdsResponseException;
import com.tencent.ads.exception.TencentAdsSDKException;
import com.tencent.ads.model.*;
import com.xiushang.marketing.test.config.TencentConfig;

import java.util.Arrays;
import java.util.List;

public class GetWechatAdvertiserLocalBusiness {
    public String ACCESS_TOKEN = TencentConfig.ACCESS_TOKEN;

    public Long accountId = TencentConfig.ACCOUNT_ID;
    /** TencentAds */
    public TencentAds tencentAds;


    public List<String> fields = Arrays.asList(
            "head_image_url",
            "name",
            "description",
            "contact_person",
            "contact_person_mobile");

    public void init() {
        this.tencentAds = TencentAds.getInstance();
        this.tencentAds.init(
                new ApiContextConfig().accessToken(ACCESS_TOKEN).isDebug(true)); // debug==true 会打印请求详细信息
        //this.tencentAds.useSandbox(); // 默认使用沙箱环境，如果要请求线上，这里需要设为线上环境
        this.tencentAds.useProduction();
        this.buildParams();
    }

    public void buildParams() {}

    public WechatAdvertiserLocalBusinessGetResponseData getWechatAdvertiserLocalBusiness()
            throws Exception {
        WechatAdvertiserLocalBusinessGetResponseData response =
                tencentAds
                        .wechatAdvertiserLocalBusiness()
                        .wechatAdvertiserLocalBusinessGet(accountId, fields);
        return response;
    }

    public static void main(String[] args) {
        try {
            GetWechatAdvertiserLocalBusiness getWechatAdvertiserLocalBusiness =
                    new GetWechatAdvertiserLocalBusiness();
            getWechatAdvertiserLocalBusiness.init();
            WechatAdvertiserLocalBusinessGetResponseData response =
                    getWechatAdvertiserLocalBusiness.getWechatAdvertiserLocalBusiness();
        } catch (TencentAdsResponseException e) {
            e.printStackTrace();
        } catch (TencentAdsSDKException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
