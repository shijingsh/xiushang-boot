package com.xiushang.common.user.vo;

import io.swagger.annotations.ApiModelProperty;

public class WxLoginVo implements java.io.Serializable{

    @ApiModelProperty(notes = "clientId")
    private String clientId;
    @ApiModelProperty(notes = "微信code")
    private String code;
    @ApiModelProperty(notes = "昵称")
    private String nickName;
    @ApiModelProperty(notes = "头像")
    private String avatarUrl;
    @ApiModelProperty(notes = "性别")
    private String gender;
    @ApiModelProperty(notes = "email")
    private String email;
    /**
     * 加密手机号码
     */
    @ApiModelProperty(notes = "加密手机号码encryptedData")
    private String encryptedData;
    @ApiModelProperty(notes = "加密手机号码iv")
    private String iv;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }
}
