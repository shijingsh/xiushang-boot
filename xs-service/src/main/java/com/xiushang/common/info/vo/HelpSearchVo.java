package com.xiushang.common.info.vo;

import com.xiushang.framework.entity.vo.SearchPageVo;
import io.swagger.annotations.ApiModelProperty;

public class HelpSearchVo extends SearchPageVo implements java.io.Serializable {

    @ApiModelProperty(value = "商铺ID")
    private String shopId;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
