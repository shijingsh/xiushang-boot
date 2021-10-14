package com.xiushang.common.upload.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 上传文件参数
 */
public class UploadBean {
    @ApiModelProperty(value = "file input 名称 （废弃）", name = "key")
    private String key;
    /**
     * 文件自定义存储路径
     */
    @ApiModelProperty(notes = "自定义文件存放路径")
    private String userPath;
    /**
     * 文件名
     */
    @ApiModelProperty(notes = "文件名",required = true)
    private String fileName ;
    /**
     * 源文件名
     */
    @ApiModelProperty(notes = "源文件名")
    private String sourceFileName ;
    /**
     * 相对路径
     */
    @ApiModelProperty(notes = "相对路径")
    private String relativePath ;
    /**
     * 绝对路径
     */
    @ApiModelProperty(notes = "绝对路径")
    private String path ;

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

    public String getSourceFileName() {
        return sourceFileName;
    }

    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
