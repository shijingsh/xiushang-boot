package com.xiushang.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 短信记录表
 */
@Entity
@Table(name="sys_sms_code")
public class SmsCodeEntity extends BaseEntity {
    /**
     * 系统短信
     * 0 系统  1 用户业务短信（可能收费）
     */

    @ApiModelProperty(notes = "是否系统短信 0 系统  1 用户业务短信")
    private int systemFlag = 0;
    /**
     * 所属商铺
     */
    @ApiModelProperty(notes = "所属商铺")
    private String shopId;
    /**
     * 接收号码
     */
    @ApiModelProperty(notes = "接收号码")
    private  String mobile;
    /**
     * 短信验证码
     */
    @ApiModelProperty(notes = "短信验证码")
    private  String smsCode;
    /**
     * 模板参数
     */
    @ApiModelProperty(notes = "模板参数")
    private String templateParam ;
    /**
     * 模板代码
     */
    @ApiModelProperty(notes = "模板代码")
    private String templateCode ;
    /**
     * 短信发送时间
     */
    @ApiModelProperty(notes = "短信发送时间")
    private  Date sendTime;

    //-------------返回内容-------------------
    @ApiModelProperty(notes = "requestId",hidden = true)
    @JSONField(serialize = false, deserialize = false)
    private String requestId;
    @ApiModelProperty(notes = "bizId",hidden = true)
    @JSONField(serialize = false, deserialize = false)
    private String bizId;

    @ApiModelProperty(notes = "code")
    private String code;
    @ApiModelProperty(notes = "message")
    private String message;


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

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
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

    public int getSystemFlag() {
        return systemFlag;
    }

    public void setSystemFlag(int systemFlag) {
        this.systemFlag = systemFlag;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
