package com.xiushang.common.user.vo;

public class SmsVo  implements java.io.Serializable {
    private String shopId;
    private int systemFlag = 0;
    private String mobile ;
    private  String smsCode;
    private String templateParam ;
    private String templateCode ;

    public SmsVo() {
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
