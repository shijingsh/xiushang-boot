package com.xiushang.common.user.vo;

import io.swagger.annotations.ApiModelProperty;

public class SmsVo  implements java.io.Serializable {
    @ApiModelProperty(notes = "商铺ID",required = true)
    private String shopId;
    @ApiModelProperty(notes = "是否为系统短信  0不是 1是")
    private int systemFlag = 0;
    @ApiModelProperty(notes = "接收短信手机号码",required = true)
    private String mobile ;
    @ApiModelProperty(notes = "短信验证码",required = true)
    private  String smsCode;
    @ApiModelProperty(notes = "短信模板参数",required = true)
    private String templateParam ;
    @ApiModelProperty(notes = "短信模板编号",required = true)
    private String templateCode ;

    public SmsVo() {
    }

    public SmsVo(String shopId, String mobile,String smsCode, String templateParam, String templateCode) {
        this.shopId = shopId;
        this.mobile = mobile;
        this.smsCode = smsCode;
        this.templateParam = templateParam;
        this.templateCode = templateCode;
    }

    public SmsVo(String shopId, String mobile, String templateParam, String templateCode) {
        this.shopId = shopId;
        this.mobile = mobile;
        this.templateParam = templateParam;
        this.templateCode = templateCode;
    }


    public SmsVo(String mobile, String templateParam, String templateCode) {
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

    public int getSystemFlag() {
        return systemFlag;
    }

    public void setSystemFlag(int systemFlag) {
        this.systemFlag = systemFlag;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
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
