package com.xiushang.common.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OAuthVo implements java.io.Serializable{

    @ApiModelProperty(notes = "授权模式（authorization_code,refresh_token,password,client_credentials,implicit,sms_code,captcha,social_type,wechat）")
    private String grant_type;
    @ApiModelProperty(notes = "Oauth2客户端ID")
    private String client_id;
    @ApiModelProperty(notes = "Oauth2客户端秘钥")
    private String client_secret;
    @ApiModelProperty(notes = "刷新token（grant_type为refresh_token必填）")
    private String refresh_token;

    @ApiModelProperty(notes = "用户名（grant_type为password 必填）")
    private String username;
    @ApiModelProperty(notes = "用户密码（grant_type为password 必填）")
    private String password;

    @ApiModelProperty(notes = "授权code、验证码、微信登陆code")
    private String code;
    @ApiModelProperty(notes = "手机号码（grant_type为sms_code必填）")
    private String mobile;

    @ApiModelProperty(notes = "重定向URL")
    private String redirect_uri;

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
    @ApiModelProperty(notes = "邮箱")
    private String email;

    @ApiModelProperty(notes = "微信加密手机号码iv（grant_type为wechat必填）")
    private String iv;
    @ApiModelProperty(notes = "微信加密手机号码encryptedData（grant_type为wechat必填）")
    private String encryptedData;

}
