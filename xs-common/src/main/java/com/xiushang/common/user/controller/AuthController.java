package com.xiushang.common.user.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.xiushang.framework.log.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.Map;

@Api(tags = "认证中心")
@ApiSort(value = 1)
@RestController
@RequestMapping(value = "/oauth",
        produces = "application/json; charset=UTF-8")
public class AuthController {
    @Autowired
    private TokenEndpoint tokenEndpoint;

    @ApiOperation(value = "OAuth2认证", notes = "租户以及用户登录入口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grant_type", defaultValue = "password", value = "授权模式（authorization_code,refresh_token,password,client_credentials,implicit,sms_code,captcha,social_type,wechat）", required = true),
            @ApiImplicitParam(name = "client_id", value = "Oauth2客户端ID", required = true),
            @ApiImplicitParam(name = "client_secret", value = "Oauth2客户端秘钥", required = true),
            @ApiImplicitParam(name = "refresh_token", value = "刷新token（grant_type为refresh_token必填）"),
            @ApiImplicitParam(name = "username", defaultValue = "", value = "用户名（grant_type为password 必填）"),
            @ApiImplicitParam(name = "password", defaultValue = "", value = "用户密码（grant_type为password 必填）"),
            @ApiImplicitParam(name = "code",  defaultValue = "",value = "授权code、验证码、微信登陆code"),
            @ApiImplicitParam(name = "redirect_uri",  defaultValue = "",value = "重定向URL"),
            @ApiImplicitParam(name = "mobile", defaultValue = "", value = "手机号码（grant_type为sms_code必填）"),
            @ApiImplicitParam(name = "socialType", defaultValue = "", value = "社交账号类型（grant_type为social_type必填）"),
            @ApiImplicitParam(name = "socialId", defaultValue = "", value = "社交账号ID（grant_type为social_type必填）"),
            @ApiImplicitParam(name = "nickName", defaultValue = "", value = "昵称（grant_type为wechat必填）"),
            @ApiImplicitParam(name = "avatarUrl", defaultValue = "", value = "头像（grant_type为wechat必填）"),
            @ApiImplicitParam(name = "gender", defaultValue = "", value = "性别（grant_type为wechat必填）"),
            @ApiImplicitParam(name = "email", defaultValue = "", value = "邮箱（grant_type为wechat必填）"),
            @ApiImplicitParam(name = "iv", defaultValue = "", value = "微信加密手机号码iv（grant_type为wechat必填）"),
            @ApiImplicitParam(name = "encryptedData", defaultValue = "", value = "微信加密手机号码encryptedData（grant_type为wechat必填）"),
    })
    @PostMapping("/token")
    public Object postAccessToken(
            @ApiIgnore Principal principal,
            @ApiIgnore @RequestParam Map<String, String> parameters
    ) throws HttpRequestMethodNotSupportedException {

        OAuth2AccessToken accessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        return CommonResult.success(accessToken);
    }

}
