package com.xiushang.common.job.vo;

import com.xiushang.framework.entity.vo.MgPageable;

/**
 * SubscribeSearchVo
 * Created by liukefu on 2018/12/7.
 */
public class SubscribeSearchVo extends MgPageable implements java.io.Serializable{

    private String userId;
    private String shopId;
    private Integer status;
    private String orderBy;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
