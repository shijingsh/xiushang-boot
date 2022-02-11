package com.xiushang.common.user.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.xiushang.common.annotations.XiushangApi;
import com.xiushang.common.exception.ServiceException;
import com.xiushang.common.service.OauthClientDetailsService;
import com.xiushang.common.user.service.UserService;
import com.xiushang.common.user.vo.OauthClientDetailsSaveVo;
import com.xiushang.entity.oauth.OauthClientDetailsEntity;
import com.xiushang.framework.entity.vo.PageTableVO;
import com.xiushang.framework.entity.vo.SearchPageVo;
import com.xiushang.framework.log.CommonResult;
import com.xiushang.framework.log.MethodResult;
import com.xiushang.security.SecurityRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户管理
 */
@Api(tags = "用户管理")
@ApiSort(value = 2)
@Controller
@RequestMapping(value = "/api/user/client",
        produces = "application/json; charset=UTF-8")
@Validated
public class ClientController {
    @Autowired
    private OauthClientDetailsService clientDetailsService;
    @Autowired
    private UserService userService;

    /**
     * 保存客户端
     * @return
     */
    @XiushangApi
    @ApiOperation(value = "保存客户端")
    @ApiOperationSupport(order=1)
    @ResponseBody
    @PostMapping("/post")
    @Secured(SecurityRole.ROLE_CLIENT_ADMIN)
    public CommonResult<OauthClientDetailsEntity> post(@Valid @RequestBody OauthClientDetailsSaveVo clientDetailsSaveVo) {

        MethodResult<OauthClientDetailsEntity> methodResult = clientDetailsService.saveClient(clientDetailsSaveVo);

        return CommonResult.success(methodResult);
    }

    /**
     * 获取客户端
     */
    @XiushangApi
    @ApiOperation(value = "获取客户端")
    @ApiOperationSupport(order=1)
    @ResponseBody
    @GetMapping("/get")
    @Secured(SecurityRole.ROLE_USER)
    public CommonResult<OauthClientDetailsEntity> getClient(@ApiParam(value = "客户端clientId",required = true) String clientId) {

        OauthClientDetailsEntity entity = clientDetailsService.findByClientId(clientId);

        String userId = userService.getCurrentUserId();
        //判断权限
        if(entity !=null && !entity.getUserId().equals(userId)){
            return CommonResult.error("无权查看客户端信息！");
        }

        return CommonResult.success(entity);
    }

    /**
     * 获取我的客户端列表
     */
    @XiushangApi
    @ApiOperation(value = "获取我的客户端列表")
    @ApiOperationSupport(order=1)
    @ResponseBody
    @PostMapping("/listPage")
    @Secured(SecurityRole.ROLE_USER)
    public CommonResult<PageTableVO> listPage(@RequestBody SearchPageVo param) {

        PageTableVO vo = clientDetailsService.findPageList(param);

        return CommonResult.success(vo);
    }

}
