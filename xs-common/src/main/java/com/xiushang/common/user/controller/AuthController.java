package com.xiushang.common.user.controller;

import com.xiushang.common.user.vo.LoginVo;
import com.xiushang.framework.log.CommonResult;
import com.xiushang.framework.log.SecurityConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "认证中心")
@RestController
@RequestMapping(value = "/oauth",
        produces = "application/json; charset=UTF-8")
public class AuthController {
    @Autowired
    private TokenEndpoint tokenEndpoint;

    @ApiOperation(value = "OAuth2认证", notes = "登录入口")
    @PostMapping("/token")
    public Object postAccessToken(
            @ApiIgnore Principal principal,
            @RequestBody LoginVo loginVo
    ) throws HttpRequestMethodNotSupportedException {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("client_id",loginVo.getClientId());
        parameters.put("client_secret",loginVo.getClientSecret());
        parameters.put("grant_type","password");
        parameters.put("username",loginVo.getLoginName());
        parameters.put("password",loginVo.getPassword());

        OAuth2AccessToken accessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        return CommonResult.success(accessToken);
    }

    @ApiOperation(value = "手机号码登陆")
    @PostMapping("/login")
    public Object login(@ApiIgnore Principal principal, @RequestBody LoginVo loginVo) {

        OAuth2AccessToken token = null;
        try {

            String clientId = loginVo.getClientId();
            String clientSecret = loginVo.getClientSecret();

            Map<String, String> parameters = new HashMap<>();
            parameters.put("client_id",clientId);
            parameters.put("client_secret",clientSecret);
            parameters.put("grant_type","password");
            parameters.put("username",loginVo.getLoginName());
            parameters.put("password",loginVo.getPassword());
            token = tokenEndpoint.postAccessToken(principal, parameters).getBody();

        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error(100003, e.getMessage());
        }

        return CommonResult.success(token);
    }

}
