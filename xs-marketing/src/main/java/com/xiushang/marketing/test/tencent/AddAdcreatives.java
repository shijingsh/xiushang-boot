package com.xiushang.marketing.test.tencent;


import com.tencent.ads.ApiContextConfig;
import com.tencent.ads.TencentAds;
import com.tencent.ads.exception.TencentAdsResponseException;
import com.tencent.ads.exception.TencentAdsSDKException;
import com.tencent.ads.model.*;
import com.tencent.ads.model.AdcreativesAddRequest;
import com.xiushang.marketing.test.config.TencentConfig;

import java.util.Arrays;
import java.util.List;

public class AddAdcreatives {
    /** YOUR ACCESS TOKEN */
    public String ACCESS_TOKEN = TencentConfig.ACCESS_TOKEN;

    public Long accountId = TencentConfig.ACCOUNT_ID;

    /** TencentAds */
    public TencentAds tencentAds;


    public AdcreativesAddRequest data = new AdcreativesAddRequest();
    public Long adcreativeTemplateId = 968L;
    public String title = "秀上app推广广告";
    public String description = "简单、快捷的推广app";
    public DestinationType pageType = DestinationType.DEFAULT;
    public String pageUrl = "https://www.xiushangsh.com";
    public PromotedObjectType promotedObjectType = PromotedObjectType.LINK_WECHAT;
    public String adcreativeName = "秀上app推广广告创意";
    public List<String> siteSet = Arrays.asList("SITE_SET_QZONE");
    public Long campaignId = 4103248870l; //推广计划ID

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

        data.setAdcreativeTemplateId(adcreativeTemplateId);

        AdcreativeCreativeElementsMp adcreativeElements = new AdcreativeCreativeElementsMp();
        adcreativeElements.setTitle(title);
        adcreativeElements.setDescription(description);
        data.setAdcreativeElements(adcreativeElements);

        data.setPageType(pageType);

        PageSpec pageSpec = new PageSpec();
        pageSpec.setPageUrl(pageUrl);
        data.setPageSpec(pageSpec);

        data.setPromotedObjectType(promotedObjectType);

        data.setAdcreativeName(adcreativeName);

        data.setSiteSet(siteSet);

        data.setCampaignId(campaignId);
    }

    public AdcreativesAddResponseData addAdcreatives() throws Exception {
        AdcreativesAddResponseData response = tencentAds.adcreatives().adcreativesAdd(data);
        return response;
    }

    public static void main(String[] args) {
        try {
            AddAdcreatives addAdcreatives = new AddAdcreatives();
            addAdcreatives.init();
            AdcreativesAddResponseData response = addAdcreatives.addAdcreatives();
        } catch (TencentAdsResponseException e) {
            e.printStackTrace();
        } catch (TencentAdsSDKException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
