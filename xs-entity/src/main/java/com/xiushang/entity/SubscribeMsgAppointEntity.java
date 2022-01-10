package com.xiushang.entity;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * 消息订阅
 */
@Entity
@Table(name="sys_subscribe_msg_appoint")
public class SubscribeMsgAppointEntity extends BaseEntity {

    /**
     * shopId
     */
    @ApiModelProperty(notes = "shopId")
    private String shopId;
    /**
     * 标题
     */
    @ApiModelProperty(notes = "标题")
    private String name;

    /**
     * 用户主键
     */
    @ApiModelProperty(notes = "用户主键")
    private String userId;

    /**
     * 微信OpenId
     */
    @ApiModelProperty(notes = "微信OpenId")
    private String openId;

    /**
     * 订阅对象ID
     */
    @ApiModelProperty(notes = "订阅对象ID")
    private String subscribeObjectId;

    /**
     * 推送状态
     * 0 未推送  1 已推送
     */
    @ApiModelProperty(notes = "推送状态")
    private Integer pullStatus = 0;

    /**
     * 订阅类型
     * 0 默认  DynamicTask 来处理，其他类型需要自定义TASK来处理
     * 1 活动
     * 2 抽奖
     */
    @ApiModelProperty(notes = "订阅类型")
    private Integer type = 0;
    /**
     * 开始时间
     */
    @ApiModelProperty(notes = "开始日期")
    private Date start;
    /**
     * 结束时间
     */
    @ApiModelProperty(notes = "结束日期")
    private Date end;

    /**
     * 订阅消息模板ID
     */
    @ApiModelProperty(notes = "订阅消息模板ID")
    private String subscribeMsgTemplateId;

    /**
     * 消息跳转的页面
     */
    @ApiModelProperty(notes = "消息跳转的页面")
    private String page;

    @ApiModelProperty(notes = "参数模板")
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String paramJson;

    @Transient
    private JSONObject paramJsonObject;

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

    public Integer getPullStatus() {
        return pullStatus;
    }

    public void setPullStatus(Integer pullStatus) {
        this.pullStatus = pullStatus;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getParamJson() {
        return paramJson;
    }

    public void setParamJson(String paramJson) {
        this.paramJson = paramJson;
    }

    public JSONObject getParamJsonObject() {
        if(StringUtils.isNotBlank(this.paramJson)){
            return  JSONObject.parseObject(this.paramJson);
        }
        return paramJsonObject;
    }

    public void setParamJsonObject(JSONObject paramJsonObject) {
        this.paramJsonObject = paramJsonObject;
    }
}
