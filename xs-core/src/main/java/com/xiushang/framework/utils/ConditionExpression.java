package com.xiushang.framework.utils;

import java.io.Serializable;


public class ConditionExpression implements Serializable {
	//模糊匹配

	//排序
	public static final String DESC = "desc";
	public static final String ASC = "asc";

	//检索方式
	public enum TYPE {
		LIKE, EQ, IN, NQ, BETWEEN,ISNULL
	}

	public enum LIKE {
		AND, OR
	}
	/***********检索条件**************/
	//检索关键字字段
	private String key;
	//检索关键词
	private String value;
	//检索类型
	private TYPE type = TYPE.EQ;
	/*************************/

	private LIKE link = LIKE.AND;

	 /***********排序条件**************/
	 //需要排序的字段
	 private String orderField;
	 // desc,  asc
	 private String order;

	public ConditionExpression(){}

	public ConditionExpression(String key, String value, TYPE type) {
		this.key = key;
		this.value = value;
		this.type = type;
	}

	public ConditionExpression(String key, String value, TYPE type,LIKE link) {
		this.key = key;
		this.value = value;
		this.type = type;
		this.link = link;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public LIKE getLink() {
		return link;
	}

	public void setLink(LIKE link) {
		this.link = link;
	}
}
