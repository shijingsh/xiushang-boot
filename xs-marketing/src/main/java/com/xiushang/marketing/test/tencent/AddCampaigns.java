package com.xiushang.marketing.test.tencent;

import com.tencent.ads.ApiContextConfig;
import com.tencent.ads.TencentAds;
import com.tencent.ads.exception.TencentAdsResponseException;
import com.tencent.ads.exception.TencentAdsSDKException;
import com.tencent.ads.model.*;
import com.tencent.ads.model.CampaignsAddRequest;
import com.xiushang.marketing.test.config.TencentConfig;

public class AddCampaigns {
    /** YOUR ACCESS TOKEN */
    public String ACCESS_TOKEN = TencentConfig.ACCESS_TOKEN;

    public Long accountId = TencentConfig.ACCOUNT_ID;
    /** TencentAds */
    public TencentAds tencentAds;

    public String campaignName = "秀上app推广计划";
    public CampaignsAddRequest data = new CampaignsAddRequest();
    public CampaignType campaignType = CampaignType.NORMAL;
    public PromotedObjectType promotedObjectType = PromotedObjectType.LINK_WECHAT;
    public Long dailyBudget = 5000L;

    public void init() {
        this.tencentAds = TencentAds.getInstance();
        this.tencentAds.init(
                new ApiContextConfig().accessToken(ACCESS_TOKEN).isDebug(true)); // debug==true 会打印请求详细信息
        //this.tencentAds.useSandbox(); // 默认使用沙箱环境，如果要请求线上，这里需要设为线上环境
        this.tencentAds.useProduction();
        this.buildParams();
    }

    public void buildParams() {
        data.setCampaignName(campaignName);

        data.setAccountId(accountId);

        data.setCampaignType(campaignType);

        data.setPromotedObjectType(promotedObjectType);

        data.setDailyBudget(dailyBudget);
    }

    public CampaignsAddResponseData addCampaigns() throws Exception {
        CampaignsAddResponseData response = tencentAds.campaigns().campaignsAdd(data);
        return response;
    }

    public static void main(String[] args) {
        try {
            AddCampaigns addCampaigns = new AddCampaigns();
            addCampaigns.init();
            CampaignsAddResponseData response = addCampaigns.addCampaigns();

/*
            {"code":0,"message":"","message_cn":"","data":{"campaign_id":4103248870},"trace_id":"d4a21e01cbfb7f12b41f46855a10178a"}
*/
        } catch (TencentAdsResponseException e) {
            e.printStackTrace();
        } catch (TencentAdsSDKException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

