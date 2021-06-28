package com.xiushang.entity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="sys_subscribe_msg_appoint")
public class SubscribeMsgAppointEntity extends BaseEntity {

    /**
     * 用户主键
     */
    @ApiModelProperty(notes = "用户主键")
    @ApiParam("用户主键")
    private String userId;

    /**
     * 微信OpenId
     */
    @ApiModelProperty(notes = "微信OpenId")
    @ApiParam("微信OpenId")
    private String openId;

    /**
     * 订阅对象ID
     */
    @ApiModelProperty(notes = "订阅对象ID")
    @ApiParam("订阅对象ID")
    private String subscribeObjectId;

    /**
     * 状态
     * 1 有限 0 无效
     */
    @ApiModelProperty(notes = "状态")
    @ApiParam("状态")
    private Integer status = 1;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSubscribeObjectId() {
        return subscribeObjectId;
    }

    public void setSubscribeObjectId(String subscribeObjectId) {
        this.subscribeObjectId = subscribeObjectId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
