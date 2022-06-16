package com.xiushang.common.user.vo;

import io.swagger.annotations.ApiModelProperty;

public class SmsCustomVo implements java.io.Serializable {
    @ApiModelProperty(notes = "接收短信手机号码",required = true)
    private String mobile ;

    @ApiModelProperty(notes = "短信模板编号（需要联系秀上创建自定义短信模板）")
    private String templateCode ;

    public SmsCustomVo() {
    }


    public SmsCustomVo(String mobile,  String templateCode) {
        this.mobile = mobile;
        this.templateCode = templateCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }
}
