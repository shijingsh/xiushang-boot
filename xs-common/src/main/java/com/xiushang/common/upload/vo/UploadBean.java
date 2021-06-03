package com.xiushang.common.upload.vo;

/**
 * 上传文件参数
 */
public class UploadBean {
    String key;
    /**
     * 文件自定义存储路径
     */
    String userPath;
    /**
     * 文件名
     */
    String fileName ;
    /**
     * 源文件名
     */
    String sourceFileName ;
    /**
     * 相对路径
     */
    String relativePath ;
    /**
     * 绝对路径
     */
    String path ;

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
