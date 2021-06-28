package com.xiushang.common.job.vo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

public class SubscribeMsgAppointVo {


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
     * 订阅消息模板ID
     */
    @ApiModelProperty(notes = "订阅消息模板ID")
    @ApiParam("订阅消息模板ID")
    private String subscribeMsgTemplateId;

    /**
     * 消息跳转的页面
     */
    @ApiModelProperty(notes = "消息跳转的页面")
    @ApiParam("消息跳转的页面")
    private String page;

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

    public String getSubscribeMsgTemplateId() {
        return subscribeMsgTemplateId;
    }

    public void setSubscribeMsgTemplateId(String subscribeMsgTemplateId) {
        this.subscribeMsgTemplateId = subscribeMsgTemplateId;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
