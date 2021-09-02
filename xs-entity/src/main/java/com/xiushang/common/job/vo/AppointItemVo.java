package com.xiushang.common.job.vo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.util.Date;

public class AppointItemVo implements java.io.Serializable{

    /**
     * 订阅类型
     * 0 默认  DynamicTask 来处理，其他类型需要自定义TASK来处理
     * 1 活动
     * 2 抽奖
     */
    @ApiModelProperty(notes = "订阅类型")
    @ApiParam("订阅类型")
    private Integer type = 0;

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

    /**
     * 参数模板
     */
    @ApiModelProperty(notes = "参数模板")
    @ApiParam("参数模板")
    private JSONObject paramJsonObject;

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

    public JSONObject getParamJsonObject() {
        return paramJsonObject;
    }

    public void setParamJsonObject(JSONObject paramJsonObject) {
        this.paramJsonObject = paramJsonObject;
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
}
