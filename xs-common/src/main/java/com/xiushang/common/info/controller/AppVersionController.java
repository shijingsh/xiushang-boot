package com.xiushang.common.info.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.xiushang.common.annotations.XiushangApi;
import com.xiushang.common.info.service.AppVersionService;
import com.xiushang.common.info.vo.AppVersionSaveVo;
import com.xiushang.common.info.vo.AppVersionVo;
import com.xiushang.common.service.OauthClientDetailsService;
import com.xiushang.common.user.service.UserService;
import com.xiushang.entity.UserEntity;
import com.xiushang.entity.info.AppVersionEntity;
import com.xiushang.entity.oauth.OauthClientDetailsEntity;
import com.xiushang.framework.log.CommonResult;

import com.xiushang.security.SecurityRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "常用接口")
@ApiSort(value = 14)
@Controller
@RequestMapping(value = "/api/app",
        produces = "application/json; charset=UTF-8")
public class AppVersionController {
    @Autowired
    private AppVersionService appVersionService;
    @Autowired
    private UserService userService;
    @Autowired
    private OauthClientDetailsService clientDetailsService;
    /**
     * 检查App版本
     * @return
     */
    @XiushangApi
    @ApiOperation(value = "检查App版本", notes = " 此接口用于客户端检查版本更新")
    @ResponseBody
    @GetMapping("/checkVersion")
    public AppVersionVo checkVersion() {

        String shopId = userService.getCurrentTenantShopId();
        String clientId = userService.getCurrentClientId();
        AppVersionEntity entity  = appVersionService.findByShopIdAndClientId(shopId,clientId);
        AppVersionVo versionVo = getVersion(entity);

        return versionVo;
    }

    /**
     * 更新App版本
     * @return
     */
    @XiushangApi
    @ApiOperation(value = "更新App版本", notes = " 版本号格式：1.0.0")
    @ResponseBody
    @PostMapping("/updateVersion")
    @Secured(SecurityRole.ROLE_USER)
    public CommonResult<AppVersionVo> updateVersion(@Valid @RequestBody AppVersionSaveVo versionSaveVo) {
        String clientId = versionSaveVo.getClientId();
        if(StringUtils.isBlank(clientId)){
            clientId = userService.getCurrentClientId();
        }
        OauthClientDetailsEntity clientDetailsEntity = clientDetailsService.findByClientId(clientId);
        if(clientDetailsEntity==null){
           return CommonResult.error("客户端不存在！");
        }
        UserEntity userEntity = userService.getCurrentUser();
        if(clientDetailsEntity!=null && !clientDetailsEntity.getUserId().equals(userEntity.getId())){
            return CommonResult.error("客户端不正确！");
        }
        String shopId = userService.getCurrentShopId();
        String version = versionSaveVo.getVersion();
        String jsVersion = versionSaveVo.getJsVersion();

        if(StringUtils.isBlank(version)){
            version = "1.0.0";
        }
        if(StringUtils.isBlank(jsVersion)){
            jsVersion = "1.0.0";
        }
        String arr[] = version.split("\\.");
        if(arr.length<3){
            return CommonResult.error("版本格式有误！");
        }
        String jsArr[] = jsVersion.split("\\.");
        if(jsArr.length<3){
            return CommonResult.error("版本格式有误！");
        }

        //获取当前版本
        AppVersionEntity entity  = appVersionService.findByShopIdAndClientId(shopId,clientId);
        if(entity  ==null){
            entity = new AppVersionEntity();
            entity.setClientId(clientId);
            entity.setShopId(shopId);
            entity.setJsVersion(jsVersion);
            entity.setVersion(version);
        }
        String oldVersion = entity.getVersion();
        String oldJsVersion = entity.getJsVersion();

        //判断当前版本是否大于旧版本
        if(isBiggerVersion(version,oldVersion)){
            entity.setVersion(version);
        }

        //jsVersion 非必填
        if(StringUtils.isBlank(jsVersion)){
            jsVersion = entity.getJsVersion();
        }
        if(isBiggerVersion(jsVersion,oldJsVersion)){
            entity.setJsVersion(jsVersion);
        }
        if(StringUtils.isNotBlank(versionSaveVo.getUrl())){
            entity.setUrl(versionSaveVo.getUrl());
        }
        if(StringUtils.isNotBlank(versionSaveVo.getJsUrl())){
            entity.setJsUrl(versionSaveVo.getJsUrl());
        }
        entity.setContent(versionSaveVo.getContent());

        appVersionService.save(entity);

        AppVersionVo versionVo = getVersion(entity);

        return CommonResult.success(versionVo);
    }

    private AppVersionVo getVersion(AppVersionEntity entity){
        AppVersionVo versionVo = new AppVersionVo();
        if(entity!=null){
            versionVo.setVersion(entity.getVersion());
            versionVo.setJsVersion(entity.getJsVersion());
            versionVo.setUrl(entity.getUrl());
            versionVo.setJsUrl(entity.getJsUrl());
            versionVo.setContent(entity.getContent());
        }else{
            versionVo.setVersion("1.0.0");
            versionVo.setJsVersion("1.0.0");
            versionVo.setUrl("");
            versionVo.setJsUrl("");
            versionVo.setContent("");
        }

        return versionVo;
    }


    public static boolean isBiggerVersion(String version,String oldVersion){
        String arr[] = version.split("\\.");
        String oldArr[] = oldVersion.split("\\.");

        int i =0;
        for (String v:oldArr){
            String nv = "";
            if(arr.length>i){
                nv = arr[i];
            }
            if(v.compareTo(nv)<0){
                return true;
            }
            i++;
        }

        return false;
    }

    public static  void main(String args[]){
        String version = "1.10.0";
        String oldVersion = "1.1.0";

        System.out.println(isBiggerVersion(version,oldVersion));
    }

}
