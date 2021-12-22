package com.xiushang.common.user.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.xiushang.common.user.vo.OAuthVo;
import com.xiushang.framework.log.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.util.HashMap;
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
    @PostMapping("/token")
    public CommonResult<OAuth2AccessToken> postAccessToken(
            @ApiIgnore Principal principal,
            OAuthVo oAuthVo
    ) throws HttpRequestMethodNotSupportedException {

        Map<String, String> parameters = new HashMap<>();

        try {
            BeanUtils.populate(oAuthVo, parameters);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        OAuth2AccessToken accessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        return CommonResult.success(accessToken);
    }

}
