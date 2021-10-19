package com.xiushang.marketing.test.tencent;

import com.tencent.ads.ApiContextConfig;
import com.tencent.ads.TencentAds;
import com.tencent.ads.exception.TencentAdsResponseException;
import com.tencent.ads.exception.TencentAdsSDKException;
import com.tencent.ads.model.*;
import com.tencent.ads.model.FilteringStruct;
import com.xiushang.marketing.test.config.TencentConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetAdgroups {
    public String ACCESS_TOKEN = TencentConfig.ACCESS_TOKEN;

    public Long accountId = TencentConfig.ACCOUNT_ID;
    /** TencentAds */
    public TencentAds tencentAds;


    public List<FilteringStruct> filtering = new ArrayList<>();

    public Long page = 1l;

    public Long pageSize = 10l;

    public Boolean isDeleted = false;

    public List<String> fields = Arrays.asList("adgroup_id", "campaign_id", "adgroup_name");

    public void init() {
        this.tencentAds = TencentAds.getInstance();
        this.tencentAds.init(
                new ApiContextConfig().accessToken(ACCESS_TOKEN).isDebug(true)); // debug==true 会打印请求详细信息
        //this.tencentAds.useSandbox(); // 默认使用沙箱环境，如果要请求线上，这里需要设为线上环境
        this.tencentAds.useProduction();
        this.buildParams();
    }

    public void buildParams() {
        String field = "promoted_object_type";
        FilteringStruct filteringStruct = new FilteringStruct();
        filteringStruct.setField(field);
        String operator = "EQUALS";
        filteringStruct.setOperator(operator);
        List<String> values = Arrays.asList("PROMOTED_OBJECT_TYPE_LINK_WECHAT");
        filteringStruct.setValues(values);
        filtering.add(filteringStruct);
    }

    public AdgroupsGetResponseData getAdgroups() throws Exception {
        AdgroupsGetResponseData response =
                tencentAds.adgroups().adgroupsGet(accountId, filtering, page, pageSize, isDeleted, fields);
        return response;
    }

    public static void main(String[] args) {
        try {
            GetAdgroups getAdgroups = new GetAdgroups();
            getAdgroups.init();
            AdgroupsGetResponseData response = getAdgroups.getAdgroups();
/*

            {"code":0,"message":"","message_cn":"","data":{"list":[{"adgroup_id":4103269908,"campaign_id":4103248870,"adgroup_name":"\u79c0\u4e0aapp\u63a8\u5e7f\u5e7f\u544a\u7ec4"}],"page_info":{"page":1,"page_size":10,"total_number":1,"total_page":1}}}
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
