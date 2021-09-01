package com.xiushang.entity;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
