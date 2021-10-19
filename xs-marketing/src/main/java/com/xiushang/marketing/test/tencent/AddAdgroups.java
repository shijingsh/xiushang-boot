package com.xiushang.marketing.test.tencent;

import com.tencent.ads.ApiContextConfig;
import com.tencent.ads.TencentAds;
import com.tencent.ads.exception.TencentAdsResponseException;
import com.tencent.ads.exception.TencentAdsSDKException;
import com.tencent.ads.model.*;
import com.tencent.ads.model.AdgroupsAddRequest;
import com.xiushang.marketing.test.config.TencentConfig;

import java.util.Arrays;
import java.util.List;

public class AddAdgroups {
    /** YOUR ACCESS TOKEN */
    public String ACCESS_TOKEN = TencentConfig.ACCESS_TOKEN;

    public Long accountId = TencentConfig.ACCOUNT_ID;
    /** TencentAds */
    public TencentAds tencentAds;

    public String endDate = "2021-10-21";
    public AdgroupsAddRequest data = new AdgroupsAddRequest();
    public OptimizationGoal optimizationGoal = OptimizationGoal.IMPRESSION;
    public List<String> userOs = Arrays.asList("IOS");

    public Long bidAmount = 150L;
    public BillingEvent billingEvent = BillingEvent.IMPRESSION;
    public String timeSeries =
            "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
    public PromotedObjectType promotedObjectType = PromotedObjectType.LINK_WECHAT;
    public String beginDate = "2021-10-20";
    public List<String> siteSet = Arrays.asList("SITE_SET_QZONE");
    public String adgroupName = "秀上app推广广告组";
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
        data.setEndDate(endDate);

        data.setOptimizationGoal(optimizationGoal);

        WriteTargetingSettingForAdgroup targeting = new WriteTargetingSettingForAdgroup();
        targeting.setUserOs(userOs);
        data.setTargeting(targeting);

        data.setAccountId(accountId);

        data.setBidAmount(bidAmount);

        data.setBillingEvent(billingEvent);

        data.setTimeSeries(timeSeries);

        data.setPromotedObjectType(promotedObjectType);

        data.setBeginDate(beginDate);

        data.setSiteSet(siteSet);

        data.setAdgroupName(adgroupName);

        data.setCampaignId(campaignId);
    }

    public AdgroupsAddResponseData addAdgroups() throws Exception {
        AdgroupsAddResponseData response = tencentAds.adgroups().adgroupsAdd(data);
        return response;
    }

    public static void main(String[] args) {
        try {
            AddAdgroups addAdgroups = new AddAdgroups();
            addAdgroups.init();
            AdgroupsAddResponseData response = addAdgroups.addAdgroups();

/*
            {"campaign_id":4103248870,"adgroup_name":"秀上app推广广告组","promoted_object_type":"PROMOTED_OBJECT_TYPE_LINK_WECHAT","begin_date":"2021-10-20","end_date":"2021-10-21","billing_event":"BILLINGEVENT_IMPRESSION","bid_amount":150,"optimization_goal":"OPTIMIZATIONGOAL_IMPRESSION","time_series":"111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","site_set":["SITE_SET_QZONE"],"targeting":{"user_os":["IOS"]},"account_id":8163787}
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

