package com.xiushang.common.job.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

public class SubscribeMsgAppointVo implements java.io.Serializable{

    /**
     * 微信OpenId
     */
    @ApiModelProperty(notes = "微信OpenId",required = true)
    private String openId;


    /**
     * 订阅对象ID
     */
    @ApiModelProperty(notes = "订阅对象ID",required = true)
    private String subscribeObjectId;

    /**
     * 订阅对象名称
     */
    @ApiModelProperty(notes = "订阅对象名称",required = true)
    private String name;

    /**
     * 订阅列表
     */
    @ApiModelProperty(notes = "订阅列表",required = true)
    private List<AppointItemVo> list = new ArrayList<>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AppointItemVo> getList() {
        return list;
    }

    public void setList(List<AppointItemVo> list) {
        this.list = list;
    }
}
