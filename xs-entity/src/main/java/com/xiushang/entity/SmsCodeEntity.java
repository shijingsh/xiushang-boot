package com.xiushang.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 短信记录表
 * Created by liukefu on 2017/6/24.
 */
@Entity
@Table(name="sys_sms_code")
public class SmsCodeEntity extends BaseEntity {
    /**
     * 系统短信
     * 0 系统  1 用户业务短信（可能收费）
     */
    private int systemFlag = 0;
    /**
     * 所属店铺
     */
    private String shopId;
    /**
     * 接收号码
     */
    private  String mobile;
    /**
     * 短信验证码
     */
    private  String smsCode;
    /**
     * 模板参数
     */
    private String templateParam ;
    /**
     * 模板代码
     */
    private String templateCode ;
    /**
     * 短信发送时间
     */
    private  Date sendTime;

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
}
