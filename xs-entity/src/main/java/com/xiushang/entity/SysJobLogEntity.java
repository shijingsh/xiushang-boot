package com.xiushang.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 定时任务日志
 *
 */
@Entity
@Table(name="sys_job_log")
public class SysJobLogEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 任务id
     */
    @ApiModelProperty(notes = "任务ID")
    private String jobId;

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
     * 任务状态    0：成功    1：失败
     */
    @ApiModelProperty(notes = "任务状态    0：成功    1：失败")
    private Integer status;

    /**
     * 失败信息
     */
    @ApiModelProperty(notes = "失败信息")
    private String error;

    /**
     * 耗时(单位：毫秒)
     */
    @ApiModelProperty(notes = "耗时(单位：毫秒)")
    private Integer times;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }
}
