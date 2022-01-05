package com.xiushang.entity;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;


/**
 * 系统参数设置
 * @author liukefu
 *
 */
@Entity
@Table(name = "sys_param")
public class SystemParamEntity extends BaseEntity {

	private static final long serialVersionUID = 5452580056240686554L;

	private String shopId;
	/**
	 * 参数中文名称
	 */
	private String paramName;
	/**
	 * 参数值
	 */
	@Lob
	private String paramValue;

	/**
	 * 参数说明
	 */
	@Lob
	private String remark;

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
