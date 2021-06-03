package com.xiushang.common.entity;

import com.xiushang.framework.entity.model.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 公司实例实体
 * @author liukefu
 */
@Entity
@Table(name="sys_instance")
public class InstanceEntity extends BaseEntity {

    /**
     * 实例名称
     */
    private String name;
    /**
     * 实例的密文（加密的实例Id）
     */
    private String token;

    /**
     * 实例状态
     * 枚举值：StatusEnum
     */
    private Integer status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
