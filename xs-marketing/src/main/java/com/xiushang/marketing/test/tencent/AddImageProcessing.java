package com.xiushang.marketing.test.tencent;

import com.tencent.ads.ApiContextConfig;
import com.tencent.ads.TencentAds;
import com.tencent.ads.exception.TencentAdsResponseException;
import com.tencent.ads.exception.TencentAdsSDKException;
import com.tencent.ads.model.*;
import com.tencent.ads.model.ImageProcessingAddRequest;
import com.xiushang.marketing.test.config.TencentConfig;

public class AddImageProcessing {
    /** YOUR ACCESS TOKEN */
    public String ACCESS_TOKEN = TencentConfig.ACCESS_TOKEN;

    /** TencentAds */
    public TencentAds tencentAds;

    public ImageProcessingAddRequest data = null;

    public void init() {
        this.tencentAds = TencentAds.getInstance();
        this.tencentAds.init(
                new ApiContextConfig().accessToken(ACCESS_TOKEN).isDebug(true)); // debug==true 会打印请求详细信息
        this.tencentAds.useSandbox(); // 默认使用沙箱环境，如果要请求线上，这里需要设为线上环境
        this.buildParams();
    }

    public void buildParams() {}

    public ImageProcessingAddResponseData addImageProcessing() throws Exception {
        ImageProcessingAddResponseData response = tencentAds.imageProcessing().imageProcessingAdd(data);
        return response;
    }

    public static void main(String[] args) {
        try {
            AddImageProcessing addImageProcessing = new AddImageProcessing();
            addImageProcessing.init();
            ImageProcessingAddResponseData response = addImageProcessing.addImageProcessing();
            System.out.println("===========================");
        } catch (TencentAdsResponseException e) {
            e.printStackTrace();
        } catch (TencentAdsSDKException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

