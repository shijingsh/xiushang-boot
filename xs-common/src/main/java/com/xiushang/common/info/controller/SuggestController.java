package com.xiushang.common.info.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.xiushang.common.annotations.XiushangApi;
import com.xiushang.common.info.service.SuggestService;
import com.xiushang.common.info.vo.SuggestProcessVo;
import com.xiushang.common.info.vo.SuggestSearchVo;
import com.xiushang.common.info.vo.SuggestVo;
import com.xiushang.entity.info.SuggestEntity;
import com.xiushang.framework.entity.vo.PageTableVO;
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
 * 用户反馈
 */
@Api(tags = "常用接口")
@ApiSort(value = 14)
@Controller
@RequestMapping(value = "/api/suggest",
        produces = "application/json; charset=UTF-8")
@Validated
public class SuggestController {
    @Autowired
    private SuggestService suggestService;

    /**
     * 获取
     * @return
     */
    @ResponseBody
    @GetMapping("/get")
    public CommonResult<SuggestEntity> get(@ApiParam(value = "id主键",required = true)String id) {
        SuggestEntity entity = suggestService.getFull(id);

        return CommonResult.success(entity);
    }

    /**
     * 保存
     * @return
     */
    @XiushangApi
    @ApiOperation(value = "保存用户反馈")
    @ResponseBody
    @PostMapping("/post")
    @Secured({SecurityRole.ROLE_CLIENT})
    public CommonResult<SuggestEntity> post(@Valid @RequestBody SuggestVo suggestVo) {

        SuggestEntity suggestEntity = suggestService.saveSuggest(suggestVo);

        return CommonResult.success(suggestEntity);
    }

    /**
     * 分页列表
     * @param param   请求
     * @return          PageTableVO
     */
    @XiushangApi
    @ApiOperation(value = "获取用户反馈列表")
    @ResponseBody
    @PostMapping("/listPage")
    @Secured({SecurityRole.ROLE_USER})
    public CommonResult<PageTableVO<SuggestEntity>> listPage(@RequestBody SuggestSearchVo param) {

        PageTableVO vo = suggestService.findPageList(param);

        return CommonResult.success(vo);
    }


    @XiushangApi
    @ApiOperation(value = "反馈处理中")
    @ResponseBody
    @GetMapping("/processing")
    @Secured({SecurityRole.ROLE_USER})
    public CommonResult<SuggestEntity> processing(@ApiParam(value = "id主键",required = true)String id ) {

        SuggestEntity entity = suggestService.processing(id);

        return CommonResult.success(entity);
    }


    @XiushangApi
    @ApiOperation(value = "反馈处理中")
    @ResponseBody
    @PostMapping("/process")
    @Secured({SecurityRole.ROLE_USER})
    public CommonResult<SuggestEntity> process(@RequestBody SuggestProcessVo suggestProcessVo ) {

        SuggestEntity entity = suggestService.process(suggestProcessVo);

        return CommonResult.success(entity);
    }
}
