package com.xiushang.entity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="sys_subscribe_msg")
public class SubscribeMsgEntity extends BaseEntity {

    /**
     * shopId
     */
    @ApiModelProperty(notes = "shopId")
    @ApiParam("shopId")
    private String shopId;
    /**
     * 标题
     */
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

    /**
     * 状态：1-即将开始；2-进行中；3-已结束；4-已取消；5-已终止
     */
    @ApiModelProperty(notes = "状态")
    @ApiParam("状态")
    private Integer status = 1;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
