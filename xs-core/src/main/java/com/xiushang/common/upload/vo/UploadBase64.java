package com.xiushang.common.upload.vo;

import io.swagger.annotations.ApiModelProperty;

public class UploadBase64 {
    @ApiModelProperty(value = "file input 名称 （废弃）", name = "key")
    private String key;
    @ApiModelProperty(notes = "自定义文件存放路径",required = true)
    private String  userPath;
    @ApiModelProperty(notes = "base64编码字符串",required = true)
    private String imgStr;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserPath() {
        return userPath;
    }

    public void setUserPath(String userPath) {
        this.userPath = userPath;
    }

    public String getImgStr() {
        return imgStr;
    }

    public void setImgStr(String imgStr) {
        this.imgStr = imgStr;
    }
}
