package com.xiushang.framework.entity.vo;

public abstract class BaseVO<T> implements java.io.Serializable {

    /**
     * 获取实例
     * @return 返回实体类
     */
    public abstract T buildEntity();

}
