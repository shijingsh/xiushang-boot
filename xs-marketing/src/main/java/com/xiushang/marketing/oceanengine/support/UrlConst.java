package com.xiushang.marketing.oceanengine.support;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
public class UrlConst {
    // auth 相关
    public static String CONNECT_OAUTH2_AUTHORIZE_URL = "https://ad.oceanengine.com/openapi/audit/oauth.html?app_id=%s&state=%s&scope=%s&redirect_uri=%s";
    public static String OAUTH2_ACCESS_TOKEN_URL = "https://ad.oceanengine.com/open_api/oauth2/access_token/";
    public static String OAUTH2_REFRESH_TOKEN_URL = "https://ad.oceanengine.com/open_api/oauth2/refresh_token/";
    public static String OAUTH2_AUTH_ADVERTISER_URL = "https://ad.oceanengine.com/open_api/oauth2/advertiser/get/?access_token=%s&app_id=%s&secret=%s";

    // 广告主相关
    public static String ADVER_INFO_QUERY_URL = "https://ad.oceanengine.com/open_api/2/advertiser/info/";
    public static String ADVER_QUALIFICATION_QUERY_URL = "https://ad.oceanengine.com/open_api/2/advertiser/qualification/select/?advertiser_id=%s";
    public static String ADVER_QUALIFICATION_CREATE_URL = "https://ad.oceanengine.com/open_api/2/advertiser/qualification/create/";
    public static String ADVER_QUALIFICATION_UPDATE_URL = "https://ad.oceanengine.com/open_api/2/advertiser/qualification/update/";
    public static String ADVER_QUALIFICATION_DELETE_URL = "https://ad.oceanengine.com/open_api/2/advertiser/qualification/delete/";
    public static String ADVER_PUBLIC_INFO_URL = "https://ad.oceanengine.com/open_api/2/advertiser/public_info/?advertiser_ids=%s";
    public static String ADVER_MAJORDOMO_INFO_URL = "https://ad.oceanengine.com/open_api/2/majordomo/advertiser/select/?advertiser_id=%s";
    public static String ADVER_BUDGET_QUERY_URL = "https://ad.oceanengine.com/open_api/2/advertiser/budget/get/?advertiser_ids=%s";


    // 代理相关
    public static String AGENT_ADVER_LIST_URL = "https://ad.oceanengine.com/open_api/2/agent/advertiser/select/?page=%s&page_size=%s";
    public static String AGENT_ADVER_CREATE_URL = "https://ad.oceanengine.com/open_api/2/agent/advertiser/create/";
    public static String AGENT_ADVER_UPDATE_URL = "https://ad.oceanengine.com/open_api/2/agent/advertiser/update/";
    public static String AGENT_CHILD_LIST_URL = "https://ad.oceanengine.com/open_api/2/agent/child_agent/select/?page=%s&page_size=%s";

    //资金流水
    public static String AGENT_TRANSFER_URL = "https://ad.oceanengine.com/open_api/2/agent/advertiser/recharge/";
    public static String AGENT_REFUND_URL = "https://ad.oceanengine.com/open_api/2/agent/advertiser/refund/";
    public static String ADVER_FOUND_BALANCE_URL = "https://ad.oceanengine.com/open_api/2/advertiser/fund/get/?advertiser_id=%s";
    public static String ADVER_FOUND_DAILY_REPORT_URL = "https://ad.oceanengine.com/open_api/2/advertiser/fund/daily_stat/";
    public static String ADVER_FOUND_TRANX_DETAIL_URL = "https://ad.oceanengine.com/open_api/2/advertiser/fund/transaction/get/";

    // 广告相关
    public static String AD_CAMPAIGN_GET_URL = "https://ad.oceanengine.com/open_api/2/campaign/get/";
    public static String AD_CAMPAIGN_CREATE_URL = "https://ad.oceanengine.com/open_api/2/campaign/create/";
    public static String AD_CAMPAIGN_UPDATE_URL = "https://ad.oceanengine.com/open_api/2/campaign/update/";
    public static String AD_CAMPAIGN_STATUS_URL = "https://ad.oceanengine.com/open_api/2/campaign/update/status/";

    public static String AD_GROUP_GET_URL = "https://ad.oceanengine.com/open_api/2/ad/get/";
    public static String AD_GROUP_CREATE_URL = "https://ad.oceanengine.com/open_api/2/ad/create/";    //创建广告计划
    public static String AD_GROUP_UPDATE_URL = "https://ad.oceanengine.com/open_api/2/ad/update/";    //修改广告计划
    public static String AD_GROUP_STATUS_URL = "https://ad.oceanengine.com/open_api/2/ad/update/status/";
    public static String AD_GROUP_BUDGET_URL = "https://ad.oceanengine.com/open_api/2/ad/update/budget/";
    public static String AD_GROUP_BID_PRICE_URL = "https://ad.oceanengine.com/open_api/2/ad/update/bid/";

    public static String AD_CREATIVE_LIST_URL = "https://ad.oceanengine.com/open_api/2/creative/select/";
    public static String AD_CREATIVE_CREATE_URL = "https://ad.oceanengine.com/open_api/2/creative/create_v2/";    //创建广告创意（新版）
    public static String AD_CREATIVE_GET_DETAIL_URL = "https://ad.oceanengine.com/open_api/2/creative/read_v2/";  //创意详细信息（新版）
    public static String AD_CREATIVE_GET_URL = "https://ad.oceanengine.com/open_api/2/creative/get/";  //获取创意列表（新版）
    public static String AD_CREATIVE_READ_URL = "https://ad.oceanengine.com/open_api/2/creative/read/";
    public static String AD_CREATIVE_UPDATE_URL = "https://ad.oceanengine.com/open_api/2/creative/update_v2/";    //修改创意信息（新版）
    public static String AD_CREATIVE_STATUS_URL = "https://ad.oceanengine.com/open_api/2/creative/update/status/";
    public static String AD_CREATIVE_MATERIAL_GET_URL = "https://ad.oceanengine.com/open_api/2/creative/material/read/";    //创意素材信息


    public static String REPORT_ADVER_URL = "https://ad.oceanengine.com/open_api/2/report/advertiser/get/";
    public static String REPORT_CAMPAIGN_URL = "https://ad.oceanengine.com/open_api/2/report/campaign/get/";
    public static String REPORT_GROUP_URL = "https://ad.oceanengine.com/open_api/2/report/ad/get/";
    public static String REPORT_CRATIVE_URL = "https://ad.oceanengine.com/open_api/2/report/creative/get/";
    public static String REPORT_AGENT_URL = "https://ad.oceanengine.com/open_api/2/report/agent/get/";
    public static String REPORT_INTEGRATED_URL = "https://ad.oceanengine.com/open_api/2/report/integrated/get/";

    //转化相关
    public static String AD_CONVERT_CREATE_URL = "https://ad.oceanengine.com/open_api/2/tools/ad_convert/create/";//创建转化ID
    public static String AD_CONVERT_UPDATE_URL = "https://ad.oceanengine.com/open_api/2/tools/ad_convert/update/";//修改转化ID
    public static String AD_CONVERT_STATUS_URL = "https://ad.oceanengine.com/open_api/2/tools/ad_convert/update_status/";//更新转化状态
    public static String AD_CONVERT_SERVERING_LIST_URL = "https://ad.oceanengine.com/open_api/2/tools/ad_convert/select/";//查询计划可用转化ID
    public static String AD_CONVERT_ADV_LIST_URL = "https://ad.oceanengine.com/open_api/2/tools/adv_convert/select/";//转化ID列表
    public static String AD_CONVERT_DETAIL_URL = "https://ad.oceanengine.com/open_api/2/tools/ad_convert/read/";//查询转化详细信息
    public static String AD_CONVERT_PUSH_URL = "https://ad.oceanengine.com/open_api/2/tools/ad_convert/push/";//查询转化详细信息


    public static String DMP_DATASOURCE_UPLOAD_URL = "https://ad.oceanengine.com/open_api/2/dmp/data_source/file/upload/";
    public static String DMP_DATASOURCE_CREATE_URL = "https://ad.oceanengine.com/open_api/2/dmp/data_source/create/";
    public static String DMP_DATASOURCE_READ_URL = "https://ad.oceanengine.com/open_api/2/dmp/data_source/read/";
    public static String DMP_AUDIENCE_LIST_URL = "https://ad.oceanengine.com/open_api/2/dmp/custom_audience/select/";
    public static String DMP_AUDIENCE_DELETE_URL = "https://ad.oceanengine.com/open_api/2/dmp/custom_audience/delete/";
    public static String DMP_AUDIENCE_LOOKALIKE_URL = "https://ad.oceanengine.com/open_api/2/dmp/custom_audience/lookalike/";
    public static String DMP_AUDIENCE_CALC_URL = "https://ad.oceanengine.com/open_api/2/dmp/custom_audience/calc/";
    public static String DMP_AUDIENCE_RULE_URL = "https://ad.oceanengine.com/open_api/2/dmp/custom_audience/rule/";
    public static String DMP_AUDIENCE_PUSH_URL = "https://ad.oceanengine.com/open_api/2/dmp/custom_audience/push_v2/";
    public static String DMP_AUDIENCE_PUBLISH_URL = "https://ad.oceanengine.com/open_api/2/dmp/custom_audience/publish/";
    public static String DMP_AUDIENCE_READ_URL = "https://ad.oceanengine.com/open_api/2/dmp/custom_audience/read/";


    public static String DMP_FILE_IMG_UPLOAD_URL = "https://ad.oceanengine.com/open_api/2/file/image/ad/";
    public static String DMP_FILE_VIDEO_UPLOAD_URL = "https://ad.oceanengine.com/open_api/2/file/video/ad/";
    public static String DMP_FILE_IMG_UPLOAD_ADV_URL = "https://ad.oceanengine.com/open_api/2/file/image/advertiser/";
    public static String DMP_FILE_IMG_GET_URL = "https://ad.oceanengine.com/open_api/2/file/image/ad/get/";
    public static String DMP_FILE_VIDEO_GET_URL = "https://ad.oceanengine.com/open_api/2/file/video/ad/get/";
    public static String DMP_FILE_VIDEO_COVER_IMG_URL = "https://ad.oceanengine.com/open_api/2/tools/video_cover/suggest/";
    public static String DMP_FILE_IMG_LIB_GET_URL = "https://ad.oceanengine.com/open_api/2/file/image/get/";
    public static String DMP_FILE_VIDEO_LIB_GET_URL = "https://ad.oceanengine.com/open_api/2/file/video/get/";

    //  动态创意词包
    public static String CREATIVE_WORD_CREATE_URL = "https://ad.oceanengine.com/open_api/2/tools/creative_word/create/";
    public static String CREATIVE_WORD_DELETE_URL = "https://ad.oceanengine.com/open_api/2/tools/creative_word/delete/";
    public static String CREATIVE_WORD_UPDATE_URL = "https://ad.oceanengine.com/open_api/2/tools/creative_word/update/";
    public static String CREATIVE_WORD_SELECT_URL = "https://ad.oceanengine.com/open_api/2/tools/creative_word/select/";


    public static String TOOLS_ESTIMATE_AUDIENCE_URL = "https://ad.oceanengine.com/open_api/2/tools/estimate_audience/";
    public static String TOOLS_AD_QUALITY_URL = "https://ad.oceanengine.com/open_api/2/tools/ad_quality/get/";
    public static String TOOLS_WEBSITE_URL = "https://ad.oceanengine.com/open_api/2/tools/site/get/";
    public static String TOOLS_INDUSTRY_URL = "https://ad.oceanengine.com/open_api/2/tools/industry/get/";

    public static String ADVERTISER_UPDATE_BUDGET_URL = "https://ad.oceanengine.com/open_api/2/advertiser/update/budget/";
}
