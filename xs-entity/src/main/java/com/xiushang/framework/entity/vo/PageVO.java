package com.xiushang.framework.entity.vo;

/**
 * 分页查询VO
 */
public class PageVO extends MgPageable {

	private String shopId;

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
}
