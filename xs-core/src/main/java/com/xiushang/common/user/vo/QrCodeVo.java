package com.xiushang.common.user.vo;

import io.swagger.annotations.ApiModelProperty;

public class QrCodeVo implements java.io.Serializable{
    @ApiModelProperty(value = "商铺ID")
    private String shopId;

    @ApiModelProperty(value = "分享地址")
    private String page;

    @ApiModelProperty(value = "参数")
    private String params;

    @ApiModelProperty(value = "code")
    private String code;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
