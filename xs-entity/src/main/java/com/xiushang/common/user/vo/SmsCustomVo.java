package com.xiushang.common.user.vo;

import io.swagger.annotations.ApiModelProperty;

public class SmsCustomVo implements java.io.Serializable {
    @ApiModelProperty(notes = "店铺ID",required = true)
    private String shopId;
    @ApiModelProperty(notes = "接收短信手机号码",required = true)
    private String mobile ;
    @ApiModelProperty(notes = "短信模板参数",required = true)
    private String templateParam ;
    @ApiModelProperty(notes = "短信模板编号（需要联系秀上创建自定义短信模板）",required = true)
    private String templateCode ;

    public SmsCustomVo() {
    }

    public SmsCustomVo(String shopId, String mobile, String templateParam, String templateCode) {
        this.shopId = shopId;
        this.mobile = mobile;
        this.templateParam = templateParam;
        this.templateCode = templateCode;
    }

    public SmsCustomVo(String mobile, String templateParam, String templateCode) {
        this.mobile = mobile;
        this.templateParam = templateParam;
        this.templateCode = templateCode;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(String templateParam) {
        this.templateParam = templateParam;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }
}
