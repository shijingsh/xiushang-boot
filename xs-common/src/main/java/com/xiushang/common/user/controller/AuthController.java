package com.xiushang.common.user.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.xiushang.common.annotations.XiushangApi;
import com.xiushang.common.service.OauthClientWhiteListService;
import com.xiushang.common.user.vo.OAuthVo;
import com.xiushang.entity.oauth.OauthClientWhiteListEntity;
import com.xiushang.framework.log.CommonResult;
import com.xiushang.framework.utils.IPUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.cglib.asm.$Type;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    @Autowired
    private OauthClientWhiteListService clientWhiteListService;
    @Autowired
    private HttpServletRequest request;

    @ApiOperation(value = "OAuth2认证中心", notes = "租户以及用户登录入口"
            + "<p>客户端授权 grant_type=client_credentials,client_id,client_secret 必填 </p>"
            + "<p>密码授权 grant_type=password,username,password 必填 </p>"
            + "<p>图形验证码密码授权 grant_type=captcha,username,password  必填 </p>"
            + "<p>授权码模式 grant_type=authorization_code,code,redirect_uri 必填 </p>"
            + "<p>短信验证码授权 grant_type=sms_code,code,mobile 必填 </p>"
            + "<p>社交账号授权 grant_type=social_type(SOCIAL_TYPE_OPEN_ID、SOCIAL_TYPE_UNION_ID、SOCIAL_TYPE_APPLE_ID),socialId,socialType,nickName,avatarUrl,gender,email,mobile,code,openId 等 其中 (mobile,code)绑定手机号码时必填 </p>"
            + "<p>微信授权 grant_type=wechat,nickName,avatarUrl,gender,email,iv,encryptedData 必填 </p>"
            + "<p>刷新token grant_type=refresh_token,refresh_token 必填 </p>"
            + "<p><font color='red'>注意： client_id,client_secret 两个参数是通过URL方式传参，即GET方式传参，其他参数通过POST json传参。所有授权方式client_id,client_secret 都是必传的。</font> </p>"
            + "<p><font color='red'>租户授权方式，请使用client_credentials、authorization_code 其他授权方式均为用户授权。</font> </p>"
    )
    @XiushangApi
    @PostMapping("/token")
    public CommonResult<OAuth2AccessToken> postAccessToken(
            @ApiIgnore Principal principal,
            @ApiParam(value = "client_id", required = true) @RequestParam String client_id,
            @ApiParam(value = "client_secret", required = true) @RequestParam String client_secret,
            @Valid @RequestBody OAuthVo oAuthVo
    ) throws HttpRequestMethodNotSupportedException {

        Map<String, String> parameters = new HashMap<>();
        try {
            parameters = objectToMap(oAuthVo);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        parameters.put("client_id", client_id);
        parameters.put("client_secret", client_secret);
        //检查白名单设置
        List<OauthClientWhiteListEntity> list = clientWhiteListService.findByClientId(client_id);
        if (list != null && list.size() > 0) {

            Boolean ipMatch = checkWhiteList(list,1);
            Boolean domainMatch = checkWhiteList(list,2);
            if(!ipMatch || !domainMatch){
                return CommonResult.error("没有客户端的使用权限，请检查白名单设置。");
            }
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
            String value = (String) field.get(obj);
            if (StringUtils.isNoneBlank(value)) {
                value = value.trim();
            }
            map.put(fieldName, value);
        }
        return map;
    }

    private List<OauthClientWhiteListEntity> getWhiteList(List<OauthClientWhiteListEntity> list, Integer type){
        List<OauthClientWhiteListEntity> rt = new ArrayList<>();
        for (OauthClientWhiteListEntity whiteListEntity:list){
            //白名单类型 1 ip白名单 2 域名白名单
            if(whiteListEntity.getType()== type){
                rt.add(whiteListEntity);
            }
        }
        return rt;
    }

    private boolean checkWhiteList(List<OauthClientWhiteListEntity> list, Integer type){
        List<OauthClientWhiteListEntity> checkList = getWhiteList(list,type);
        if(checkList.size()==0){
            return true;
        }
        String ip = IPUtils.getIpAddr(request);

        String domain = request.getServerName();

        Boolean match = false;
        for (OauthClientWhiteListEntity whiteListEntity:checkList){
            //白名单类型 1 ip白名单 2 域名白名单
            if(whiteListEntity.getType()==1 && ip.equals(whiteListEntity.getIpOrDomain())){
                match = true;
                break;
            }else  if(whiteListEntity.getType()==2 && whiteListEntity.getIpOrDomain().indexOf(domain) >=0){
                match = true;
                break;
            }
        }

        return match;
    }
}
