package com.xiushang.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;


/**
 * 参数设置
 */
@Entity
@Table(name = "sys_param")
public class SystemParamEntity extends BaseEntity {

	private static final long serialVersionUID = 5452580056240686554L;

	@ApiModelProperty(notes = "商铺ID")
	private String shopId;

	/**
	 * 参数中文名称
	 */
	@ApiModelProperty(notes = "参数名称")
	private String paramName;
	/**
	 * 参数值
	 */
	@Lob
	@ApiModelProperty(notes = "参数值")
	private String paramValue;

	/**
	 * 参数说明
	 */
	@ApiModelProperty(notes = "备注")
	private String remark;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

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
