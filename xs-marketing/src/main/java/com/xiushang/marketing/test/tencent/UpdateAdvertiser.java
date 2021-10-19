package com.xiushang.marketing.test.tencent;


import com.tencent.ads.ApiContextConfig;
import com.tencent.ads.TencentAds;
import com.tencent.ads.exception.TencentAdsResponseException;
import com.tencent.ads.exception.TencentAdsSDKException;
import com.tencent.ads.model.*;
import com.tencent.ads.model.AdvertiserUpdateRequest;
import com.xiushang.marketing.test.config.TencentConfig;

public class UpdateAdvertiser {
    public String ACCESS_TOKEN = TencentConfig.ACCESS_TOKEN;

    public Long accountId = TencentConfig.ACCOUNT_ID;
    /** TencentAds */
    public TencentAds tencentAds;

    public AdvertiserUpdateRequest data = new AdvertiserUpdateRequest();
    public String introductionUrl = "YOUR INTRODUCTION PAGE URL";

    public void init() {
        this.tencentAds = TencentAds.getInstance();
        this.tencentAds.init(
                new ApiContextConfig().accessToken(ACCESS_TOKEN).isDebug(true)); // debug==true 会打印请求详细信息
        this.tencentAds.useSandbox(); // 默认使用沙箱环境，如果要请求线上，这里需要设为线上环境
        this.buildParams();
    }

    public void buildParams() {
        data.setAccountId(accountId);

        data.setIntroductionUrl(introductionUrl);
    }

    public AdvertiserUpdateResponseData updateAdvertiser() throws Exception {
        AdvertiserUpdateResponseData response = tencentAds.advertiser().advertiserUpdate(data);
        return response;
    }

    public static void main(String[] args) {
        try {
            UpdateAdvertiser updateAdvertiser = new UpdateAdvertiser();
            updateAdvertiser.init();
            AdvertiserUpdateResponseData response = updateAdvertiser.updateAdvertiser();
        } catch (TencentAdsResponseException e) {
            e.printStackTrace();
        } catch (TencentAdsSDKException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

