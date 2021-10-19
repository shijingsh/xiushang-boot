package com.xiushang.marketing.test.tencent;

import com.tencent.ads.ApiContextConfig;
import com.tencent.ads.TencentAds;
import com.tencent.ads.exception.TencentAdsResponseException;
import com.tencent.ads.exception.TencentAdsSDKException;
import com.tencent.ads.model.*;
import com.tencent.ads.model.AdsAddRequest;
import com.xiushang.marketing.test.config.TencentConfig;

public class AddAds {
    /** YOUR ACCESS TOKEN */
    public String ACCESS_TOKEN = TencentConfig.ACCESS_TOKEN;

    public Long accountId = TencentConfig.ACCOUNT_ID;
    /** TencentAds */
    public TencentAds tencentAds;

    public AdsAddRequest data = new AdsAddRequest();
    public Long adgroupId = 4103269908l;    //广告组ID
    public Long adcreativeId = null;        //广告创意ID
    public String adName = "秀上广告";

    public void init() {
        this.tencentAds = TencentAds.getInstance();
        this.tencentAds.init(
                new ApiContextConfig().accessToken(ACCESS_TOKEN).isDebug(true)); // debug==true 会打印请求详细信息
        //this.tencentAds.useSandbox(); // 默认使用沙箱环境，如果要请求线上，这里需要设为线上环境
        this.tencentAds.useProduction();
        this.buildParams();
    }

    public void buildParams() {
        data.setAccountId(accountId);

        data.setAdgroupId(adgroupId);

        data.setAdcreativeId(adcreativeId);

        data.setAdName(adName);
    }

    public AdsAddResponseData addAds() throws Exception {
        AdsAddResponseData response = tencentAds.ads().adsAdd(data);
        return response;
    }

    public static void main(String[] args) {
        try {
            AddAds addAds = new AddAds();
            addAds.init();
            AdsAddResponseData response = addAds.addAds();
        } catch (TencentAdsResponseException e) {
            e.printStackTrace();
        } catch (TencentAdsSDKException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
