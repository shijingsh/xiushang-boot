package com.xiushang.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 验证码记录
 * Created by liukefu on 2017/6/24.
 */
@Entity
@Table(name="sys_sms_code")
public class SmsCodeEntity extends BaseEntity {

    private  String mobile;
    private  String smsCode;
    private  Date sendTime;

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
}
