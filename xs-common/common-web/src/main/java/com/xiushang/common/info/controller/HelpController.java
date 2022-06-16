package com.xiushang.common.info.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.xiushang.common.annotations.XiushangApi;
import com.xiushang.common.info.service.HelpService;
import com.xiushang.common.info.vo.HelpVo;
import com.xiushang.framework.entity.vo.SearchPageVo;
import com.xiushang.framework.log.CommonResult;
import com.xiushang.entity.info.HelpEntity;
import com.xiushang.framework.entity.vo.PageTableVO;


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

@Api(tags = "常用接口")
@ApiSort(value = 14)
@Controller
@RequestMapping(value = "/api/help",
        produces = "application/json; charset=UTF-8")
@Validated
public class HelpController {
    @Autowired
    private HelpService helpService;
    /**
     * 获取
     * @return
     */
    @ResponseBody
    @GetMapping("/get")
    public CommonResult<HelpEntity> get(@ApiParam(value = "id主键",required = true)String id) {
        HelpEntity entity = helpService.get(id);

        return CommonResult.success(entity);
    }
    /**
     * 删除
     * @return
     */
    @XiushangApi
    @ApiOperation(value = "删除帮助信息")
    @ResponseBody
    @GetMapping("/delete")
    @Secured(SecurityRole.ROLE_USER)
    public CommonResult<HelpEntity> delete(@ApiParam(value = "id主键",required = true)String id) {
        helpService.delete(id);
        return CommonResult.success();
    }
    /**
     * 保存
     * @return
     */
    @XiushangApi
    @ApiOperation(value = "保存帮助信息")
    @ResponseBody
    @PostMapping("/post")
    @Secured(SecurityRole.ROLE_USER)
    public CommonResult<HelpEntity> post(@Valid @RequestBody HelpVo helpVo) {

        HelpEntity helpEntity = helpService.saveHelp(helpVo);
        return CommonResult.success(helpEntity);
    }
    /**
     * 获取租户帮助信息列表
     * @param searchPageVo
     * @return          PageTableVO
     */
    @XiushangApi
    @ApiOperation(value = "获取租户帮助信息列表")
    @ResponseBody
    @PostMapping("/listPage")
    public CommonResult<PageTableVO<HelpEntity>> listPage(@RequestBody SearchPageVo searchPageVo) {

        PageTableVO vo = helpService.findPageList(searchPageVo);

        return CommonResult.success(vo);
    }


    /**
     * 获取我的帮助列表
     * @param searchPageVo
     * @return          PageTableVO
     */
    @ResponseBody
    @PostMapping("/myListPage")
    @Secured(SecurityRole.ROLE_CLIENT_MANAGE)
    public CommonResult<PageTableVO<HelpEntity>> shopListPage(@RequestBody SearchPageVo searchPageVo) {

        PageTableVO vo = helpService.findMyPageList(searchPageVo);

        return CommonResult.success(vo);
    }


}
