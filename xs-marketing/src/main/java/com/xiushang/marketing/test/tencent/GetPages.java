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

public class GetPages {
    public String ACCESS_TOKEN = TencentConfig.ACCESS_TOKEN;

    public Long accountId = TencentConfig.ACCOUNT_ID;
    /** TencentAds */
    public TencentAds tencentAds;

    public String promotedObjectType = "PROMOTED_OBJECT_TYPE_LINK";

    public List<FilteringStruct> filtering = null;

    public Long page = 1l;

    public Long pageSize = 10l;

    public List<String> fields =
            Arrays.asList(
                    "page_id",
                    "page_name",
                    "preview_url",
                    "created_time",
                    "last_modified_time",
                    "promoted_object_id",
                    "page_type");

    public void init() {
        this.tencentAds = TencentAds.getInstance();
        this.tencentAds.init(
                new ApiContextConfig().accessToken(ACCESS_TOKEN).isDebug(true)); // debug==true 会打印请求详细信息
        //this.tencentAds.useSandbox(); // 默认使用沙箱环境，如果要请求线上，这里需要设为线上环境
        this.tencentAds.useProduction();
        this.buildParams();
    }

    public void buildParams() {}

    public PagesGetResponseData getPages() throws Exception {
        PagesGetResponseData response =
                tencentAds
                        .pages()
                        .pagesGet(accountId, promotedObjectType, filtering, page, pageSize, fields);
        return response;
    }

    public static void main(String[] args) {
        try {
            GetPages getPages = new GetPages();
            getPages.init();
            PagesGetResponseData response = getPages.getPages();
        } catch (TencentAdsResponseException e) {
            e.printStackTrace();
        } catch (TencentAdsSDKException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

