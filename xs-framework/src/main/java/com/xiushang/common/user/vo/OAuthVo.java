package com.xiushang.common.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class OAuthVo implements java.io.Serializable{

    /**
     * 授权模式
     * ,implicit
     */
    @ApiModelProperty(notes = "授权模式（authorization_code,refresh_token,password,client_credentials,sms_code,captcha,social_type,wechat）",position = 1,required=true)
    private String grant_type;

    @ApiModelProperty(notes = "刷新token（grant_type为refresh_token必填）",position = 20)
    private String refresh_token;

    @ApiModelProperty(notes = "用户名（grant_type为password 必填）",position = 5)
    private String username;
    @ApiModelProperty(notes = "用户密码（grant_type为password 必填）",position = 6)
    private String password;

    @ApiModelProperty(notes = "授权code、验证码、微信登录code",position = 7)
    private String code;
    @ApiModelProperty(notes = "手机号码（grant_type为sms_code必填）",position = 8)
    private String mobile;

    @ApiModelProperty(notes = "重定向URL",position = 9)
    private String redirect_uri;

    @ApiModelProperty(notes = "社交账号ID",position = 10)
    private String socialId;
    @ApiModelProperty(notes = "社交账号Type",position = 11)
    private String socialType;

    @ApiModelProperty(notes = "微信登录时，传openId")
    private String openId;

    @ApiModelProperty(notes = "昵称",position = 12)
    private String nickName;
    @ApiModelProperty(notes = "头像",position = 13)
    private String avatarUrl;
    @ApiModelProperty(notes = "性别",position = 14)
    private String gender;
    @ApiModelProperty(notes = "邮箱",position = 15)
    private String email;

    @ApiModelProperty(notes = "微信加密手机号码iv（grant_type为wechat必填）",position = 16)
    private String iv;
    @ApiModelProperty(notes = "微信加密手机号码encryptedData（grant_type为wechat必填）",position = 17)
    private String encryptedData;

}
