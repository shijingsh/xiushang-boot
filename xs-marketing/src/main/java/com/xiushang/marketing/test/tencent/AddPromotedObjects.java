package com.xiushang.marketing.test.tencent;

import com.tencent.ads.ApiContextConfig;
import com.tencent.ads.TencentAds;
import com.tencent.ads.exception.TencentAdsResponseException;
import com.tencent.ads.exception.TencentAdsSDKException;
import com.tencent.ads.model.*;
import com.tencent.ads.model.PromotedObjectsAddRequest;
import com.xiushang.marketing.test.config.TencentConfig;

public class AddPromotedObjects {
    /** YOUR ACCESS TOKEN */
    public String ACCESS_TOKEN = TencentConfig.ACCESS_TOKEN;

    public Long accountId = TencentConfig.ACCOUNT_ID;
    /** TencentAds */
    public TencentAds tencentAds;

    public String promotedObjectId = "1112002981";
    public PromotedObjectsAddRequest data = new PromotedObjectsAddRequest();
    public PromotedObjectType promotedObjectType = PromotedObjectType.APP_IOS;

    public void init() {
        this.tencentAds = TencentAds.getInstance();
        this.tencentAds.init(
                new ApiContextConfig().accessToken(ACCESS_TOKEN).isDebug(true)); // debug==true 会打印请求详细信息
        //this.tencentAds.useSandbox(); // 默认使用沙箱环境，如果要请求线上，这里需要设为线上环境
        this.tencentAds.useProduction();
        this.buildParams();
    }

    public void buildParams() {
        data.setPromotedObjectId(promotedObjectId);

        data.setAccountId(accountId);

        data.setPromotedObjectType(promotedObjectType);
    }

    public PromotedObjectsAddResponseData addPromotedObjects() throws Exception {
        PromotedObjectsAddResponseData response = tencentAds.promotedObjects().promotedObjectsAdd(data);
        return response;
    }

    public static void main(String[] args) {
        try {
            AddPromotedObjects addPromotedObjects = new AddPromotedObjects();
            addPromotedObjects.init();
            PromotedObjectsAddResponseData response = addPromotedObjects.addPromotedObjects();
        } catch (TencentAdsResponseException e) {
            e.printStackTrace();
        } catch (TencentAdsSDKException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
