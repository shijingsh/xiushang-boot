package com.xiushang.common.user.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.xiushang.common.annotations.XiushangApi;
import com.xiushang.common.user.service.SystemParamService;
import com.xiushang.common.user.vo.SystemParamSaveVo;
import com.xiushang.common.user.vo.SystemParamVo;
import com.xiushang.entity.SystemParamEntity;
import com.xiushang.framework.entity.vo.PageTableVO;
import com.xiushang.framework.entity.vo.SearchPageVo;
import com.xiushang.framework.log.CommonResult;
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
 * 用户参数设置
 */
@Api(tags = "常用接口")
@ApiSort(value = 14)
@Controller
@RequestMapping(value = "/api/param",
        produces = "application/json; charset=UTF-8")
@Validated
public class ParamController {
    @Autowired
    private SystemParamService systemParamService;


    /**
     * 保存
     * @return
     */
    @XiushangApi
    @ApiOperation(value = "保存参数设置")
    @ApiOperationSupport(order=999)
    @ResponseBody
    @PostMapping("/post")
    @Secured(SecurityRole.ROLE_USER)
    public CommonResult<SystemParamEntity> post(@Valid @RequestBody SystemParamSaveVo systemParamSaveVo) {

        SystemParamEntity paramEntity = systemParamService.saveParam(systemParamSaveVo);
        return CommonResult.success(paramEntity);
    }

    /**
     * 获取用户参数
     */
    @XiushangApi
    @ApiOperation(value = "获取用户参数")
    @ApiOperationSupport(order=999)
    @ResponseBody
    @PostMapping("/getOrSaveParam")
    @Secured(SecurityRole.ROLE_USER)
    public CommonResult<SystemParamEntity> getOrSaveParam(@Valid @RequestBody SystemParamVo systemParamVo) {

        SystemParamEntity entity = systemParamService.getOrSaveParam(systemParamVo.getParamName(),systemParamVo.getDefaultValue(),systemParamVo.getRemark());

        return CommonResult.success(entity);
    }

    /**
     * 删除
     * @return
     */
    @ApiOperation(value = "删除用户参数")
    @ApiOperationSupport(order=999)
    @ResponseBody
    @GetMapping("/delete")
    @Secured({SecurityRole.ROLE_USER})
    public CommonResult<SystemParamEntity> delete(@ApiParam(value = "id主键",required = true) String id) {
        systemParamService.delete(id);
        return CommonResult.success();
    }

    /**
     * 获取我的参数列表
     */
    @XiushangApi
    @ApiOperation(value = "获取我的参数列表")
    @ApiOperationSupport(order=999)
    @ResponseBody
    @PostMapping("/listPage")
    @Secured(SecurityRole.ROLE_USER)
    public CommonResult<PageTableVO> listPage(@RequestBody SearchPageVo param) {

        PageTableVO vo = systemParamService.findPageList(param);

        return CommonResult.success(vo);
    }

}
