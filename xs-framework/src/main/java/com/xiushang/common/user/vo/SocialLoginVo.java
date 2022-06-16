package com.xiushang.common.user.vo;

import io.swagger.annotations.ApiModelProperty;

public class SocialLoginVo implements java.io.Serializable{

    @ApiModelProperty(notes = "clientId")
    private String clientId;
    @ApiModelProperty(notes = "社交账号ID")
    private String socialId;
    @ApiModelProperty(notes = "社交账号Type")
    private String socialType;
    @ApiModelProperty(notes = "昵称")
    private String nickName;
    @ApiModelProperty(notes = "头像")
    private String avatarUrl;
    @ApiModelProperty(notes = "性别")
    private String gender;
    @ApiModelProperty(notes = "email")
    private String email;
    @ApiModelProperty(notes = "mobile")
    private String mobile;
    @ApiModelProperty(notes = "短信验证码")
    private String code;

    @ApiModelProperty(notes = "微信登录时，传openId")
    private String openId;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getSocialType() {
        return socialType;
    }

    public void setSocialType(String socialType) {
        this.socialType = socialType;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
