package com.xiushang.marketing.test.tencent;

import com.tencent.ads.ApiContextConfig;
import com.tencent.ads.TencentAds;
import com.tencent.ads.exception.TencentAdsResponseException;
import com.tencent.ads.exception.TencentAdsSDKException;
import com.tencent.ads.model.*;
import com.xiushang.marketing.test.config.TencentConfig;

import java.io.File;

public class AddImages {
    /** YOUR ACCESS TOKEN */
    public String ACCESS_TOKEN = TencentConfig.ACCESS_TOKEN;

    public Long accountId = TencentConfig.ACCOUNT_ID;
    /** TencentAds */
    public TencentAds tencentAds;


    public String uploadType = "UPLOAD_TYPE_FILE";

    public String signature = null;

    public File file = new File("C:\\Users\\liukefu\\Desktop\\img\\shareLogo.png");

    public String bytes = null;

    public String imageUsage = null;

    public String description = null;

    public void init() {
        this.tencentAds = TencentAds.getInstance();
        this.tencentAds.init(
                new ApiContextConfig().accessToken(ACCESS_TOKEN).isDebug(true)); // debug==true 会打印请求详细信息
        //this.tencentAds.useSandbox(); // 默认使用沙箱环境，如果要请求线上，这里需要设为线上环境
        this.tencentAds.useProduction();
        this.buildParams();
    }

    public void buildParams() {}

    public ImagesAddResponseData addImages() throws Exception {
        signature = FileMD5.getMd5ByFile(file);
        ImagesAddResponseData response =
                tencentAds
                        .images()
                        .imagesAdd(accountId, uploadType, signature, file, bytes, imageUsage, description);
        return response;
    }

    public static void main(String[] args) {
        try {
            AddImages addImages = new AddImages();
            addImages.init();
            ImagesAddResponseData response = addImages.addImages();
        } catch (TencentAdsResponseException e) {
            e.printStackTrace();
        } catch (TencentAdsSDKException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

