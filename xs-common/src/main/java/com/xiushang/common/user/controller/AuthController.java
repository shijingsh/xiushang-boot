package com.xiushang.common.user.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.xiushang.common.user.vo.OAuthVo;
import com.xiushang.framework.log.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "认证中心")
@ApiSort(value = 1)
@RestController
@RequestMapping(value = "/oauth",
        produces = "application/json; charset=UTF-8")
@Validated
public class AuthController {
    @Autowired
    private TokenEndpoint tokenEndpoint;

    @ApiOperation(value = "OAuth2认证", notes = "租户以及用户登录入口")
    @PostMapping("/token")
    public CommonResult<OAuth2AccessToken> postAccessToken(
            @ApiIgnore Principal principal,
            @ApiParam(value = "client_id",required = true) @RequestParam String  client_id,
            @ApiParam(value = "client_secret",required = true) @RequestParam String  client_secret,
            @Valid @RequestBody OAuthVo oAuthVo
    ) throws HttpRequestMethodNotSupportedException {

        Map<String, String> parameters = new HashMap<>();
        try {
           parameters = objectToMap(oAuthVo);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        parameters.put("client_id",client_id);
        parameters.put("client_secret",client_secret);

        OAuth2AccessToken accessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        return CommonResult.success(accessToken);
    }

    /**
     * 将Object对象里面的属性和值转化成Map对象
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, String> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, String> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            String value = (String)field.get(obj);
            map.put(fieldName, value);
        }
        return map;
    }

}
