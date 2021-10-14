package com.xiushang.common.user.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 第三方登录用户
 * Created by liukefu on 2018/8/11.
 */
public class ThirdUserVo implements java.io.Serializable{
    @ApiModelProperty(notes = "openId")
    private String openId;
    @ApiModelProperty(notes = "unionId")
    private String unionId;
    @ApiModelProperty(notes = "appleId")
    private String appleId;
    @ApiModelProperty(notes = "账号登录名")
    private String loginName;
    @ApiModelProperty(notes = "姓名、昵称")
    private String userName;
    @ApiModelProperty(notes = "第三方登录token")
    private String accessToken;
    @ApiModelProperty(notes = "头像")
    private String userAvatar;
    @ApiModelProperty(notes = "性别")
    private String userGender;
    @ApiModelProperty(notes = "手机号")
    private String mobile;
    @ApiModelProperty(notes = "email")
    private String email;
    @ApiModelProperty(notes = "验证码")
    private String verifyCode;
    /**
     * 多实例用户token
     */
    @ApiModelProperty(notes = "多实例用户token")
    private String userToken;

    /**
     * 最后登录平台
     */
    @ApiModelProperty(notes = "最后登录平台")
    private String lastLoginPlatform;
    /**
     * 推送 clientId
     */
    @ApiModelProperty(notes = "推送 clientId")
    private String clientId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getAppleId() {
        return appleId;
    }

    public void setAppleId(String appleId) {
        this.appleId = appleId;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getLastLoginPlatform() {
        return lastLoginPlatform;
    }

    public void setLastLoginPlatform(String lastLoginPlatform) {
        this.lastLoginPlatform = lastLoginPlatform;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
