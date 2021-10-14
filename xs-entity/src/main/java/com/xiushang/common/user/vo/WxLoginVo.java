package com.xiushang.common.user.vo;

import io.swagger.annotations.ApiModelProperty;

public class WxLoginVo implements java.io.Serializable{

    @ApiModelProperty(notes = "shopId")
    private String shopId;
    @ApiModelProperty(notes = "微信code")
    private String code;
    @ApiModelProperty(notes = "userToken")
    private String userToken;
    @ApiModelProperty(notes = "昵称")
    private String nickName;
    @ApiModelProperty(notes = "头像")
    private String avatarUrl;
    @ApiModelProperty(notes = "性别")
    private String gender;
    @ApiModelProperty(notes = "手机号")
    private String mobile;
    @ApiModelProperty(notes = "email")
    private String email;
    @ApiModelProperty(notes = "最后登录平台")
    private String lastLoginPlatform;
    /**
     * 加密手机号码
     */
    @ApiModelProperty(notes = "加密手机号码encryptedData")
    private String encryptedData;
    @ApiModelProperty(notes = "加密手机号码iv")
    private String iv;

    /**
     * 推送 clientId
     */
    @ApiModelProperty(notes = "推送 clientId")
    private String clientId;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getLastLoginPlatform() {
        return lastLoginPlatform;
    }

    public void setLastLoginPlatform(String lastLoginPlatform) {
        this.lastLoginPlatform = lastLoginPlatform;
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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
