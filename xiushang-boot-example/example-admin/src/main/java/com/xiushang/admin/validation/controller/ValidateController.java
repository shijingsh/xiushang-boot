package com.xiushang.admin.validation.controller;

import com.xiushang.admin.validation.vo.ParamVo;
import com.xiushang.framework.log.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Api(tags = {"常用接口"})
@Validated
@Controller
@RequestMapping(value = "/api/valid",
        produces = "application/json; charset=UTF-8")
public class ValidateController {

	@ApiOperation(value = "ApiModelProperty必填校验")
    @PostMapping(value = "/apiModelProperty")
	@ResponseBody
    public CommonResult valApiModelProperty(@Valid @RequestBody ParamVo paramVo) {

        return CommonResult.success();
    }

    @ApiOperation(value = "ApiParam必填校验")
    @GetMapping(value = "/apiParam")
    @ResponseBody
    public CommonResult valApiParam(@ApiParam(value = "id主键",required = true) String id) {

        return CommonResult.success();
    }
}
