package com.xiushang.common.info.vo;

import io.swagger.annotations.ApiModelProperty;

public class AppVersionVo implements java.io.Serializable {

    @ApiModelProperty(value = "版本号")
    private String version;
    @ApiModelProperty(value = "js版本号")
    private String jsVersion;
    @ApiModelProperty(value = "更新下载地址")
    private String url;
    @ApiModelProperty(value = "js更新下载地址（热更新）")
    private String jsUrl;
    @ApiModelProperty(value = "更新版本说明")
    private String content;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getJsVersion() {
        return jsVersion;
    }

    public void setJsVersion(String jsVersion) {
        this.jsVersion = jsVersion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJsUrl() {
        return jsUrl;
    }

    public void setJsUrl(String jsUrl) {
        this.jsUrl = jsUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
