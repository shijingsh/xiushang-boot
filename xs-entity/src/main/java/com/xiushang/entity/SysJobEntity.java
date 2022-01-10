package com.xiushang.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 定时任务
 *
 */
@Entity
@Table(name="sys_job")
public class SysJobEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 任务调度参数key
     */
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";


    /**
     * spring bean名称
     */
    @ApiModelProperty(notes = "任务处理类")
    private String beanName;

    /**
     * 参数
     */
    @ApiModelProperty(notes = "参数")
    private String params;

    /**
     * cron表达式
     */
    @ApiModelProperty(notes = "cron表达式")
    private String cronExpression;

    /**
     * 任务状态  0：正常  1：暂停
     */
    @ApiModelProperty(notes = "任务状态    0：成功    1：失败")
    private Integer status;

    /**
     * 备注
     */
    @ApiModelProperty(notes = "备注")
    private String remark;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
