package com.xiushang.common.job.vo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.util.Date;

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

    @ApiModelProperty(notes = "参数模板")
    @ApiParam("参数模板")
    private JSONObject paramJsonObject;

    //-------------------消息内容------------------------------
    @ApiModelProperty(notes = "shopId")
    @ApiParam("shopId")
    private String shopId;

    @ApiModelProperty(notes = "标题")
    @ApiParam("标题")
    private String name;
    /**
     * 开始时间
     */
    @ApiModelProperty(notes = "开始日期")
    @ApiParam("开始日期")
    private Date start;
    /**
     * 结束时间
     */
    @ApiModelProperty(notes = "结束日期")
    @ApiParam("结束日期")
    private Date end;


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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public JSONObject getParamJsonObject() {
        return paramJsonObject;
    }

    public void setParamJsonObject(JSONObject paramJsonObject) {
        this.paramJsonObject = paramJsonObject;
    }
}
