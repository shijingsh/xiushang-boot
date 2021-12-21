package com.xiushang.vo;

import com.xiushang.framework.entity.vo.SearchPageVo;

/**
 * Created by liukefu on 2018/9/7.
 */
public class HelpSearchVo extends SearchPageVo implements java.io.Serializable {

    private String shopId;
    private String searchKey;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
}
