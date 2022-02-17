package com.xiushang.common.user.controller;

import cn.hutool.core.util.StrUtil;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.xiushang.common.annotations.XiushangApi;
import com.xiushang.common.user.vo.OAuthVo;
import com.xiushang.framework.log.CommonResult;
import com.xiushang.framework.log.SecurityConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Base64;
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
    @Resource
    private HttpServletRequest request;

    @ApiOperation(value = "OAuth2认证中心", notes = "租户以及用户登录入口"
            +"<p>客户端授权 grant_type=client_credentials,client_id,client_secret 必填 </p>"
            +"<p>密码授权 grant_type=password,username,password 必填 </p>"
            +"<p>图形验证码密码授权 grant_type=captcha,username,password  必填 </p>"
            +"<p>授权码模式 grant_type=authorization_code,code,redirect_uri 必填 </p>"
            +"<p>短信验证码授权 grant_type=sms_code,code,mobile 必填 </p>"
            +"<p>社交账号授权 grant_type=social_type,socialId,socialType,nickName,avatarUrl,gender,email 必填 </p>"
            +"<p>微信授权 grant_type=wechat,nickName,avatarUrl,gender,email,iv,encryptedData 必填 </p>"
            +"<p>刷新token grant_type=refresh_token,refresh_token 必填 </p>"
            +"<p><font color='red'>注意： client_id,client_secret 两个参数是通过URL方式传参，即GET方式传参，其他参数通过POST json传参。所有授权方式client_id,client_secret 都是必传的。</font> </p>"
            +"<p><font color='red'>租户授权方式，请使用client_credentials、authorization_code 其他授权方式均为用户授权。</font> </p>"
    )
    @XiushangApi
    @PostMapping("/token")
    public CommonResult<OAuth2AccessToken> postAccessToken(
            @ApiIgnore Principal principal,
            @Valid @RequestBody OAuthVo oAuthVo
    ) throws HttpRequestMethodNotSupportedException {

        Map<String, String> parameters = new HashMap<>();
        try {
           parameters = objectToMap(oAuthVo);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        String clientId = AuthController.getOAuth2ClientId(request);
        if(StringUtils.isBlank(clientId)){
            CommonResult.error("clientId 不能为空！");
        }

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
            if(StringUtils.isNoneBlank(value)){
                value = value.trim();
            }
            map.put(fieldName, value);
        }
        return map;
    }

    public static String getOAuth2ClientId(HttpServletRequest request) {

        String clientId = null;
        // 从请求头获取
        String basic = request.getHeader(SecurityConstants.AUTH_HEADER_STRING);
        if (StrUtil.isNotBlank(basic) && basic.startsWith(SecurityConstants.AUTH_HEADER_BASIC_PREFIX)) {
            basic = basic.replace(SecurityConstants.AUTH_HEADER_BASIC_PREFIX, Strings.EMPTY);
            String basicPlainText = new String(Base64.getDecoder().decode(basic.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            clientId = basicPlainText.split(":")[0]; //client:secret
        }
        return clientId;
    }
}
