package com.xiushang.common.user.vo;

public class WaterMark {
    private Long timestamp;// 时间戳做转换的时候，记得先乘以1000，再通过simpledateformat完成date类型转换
    private String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
