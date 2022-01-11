package com.xiushang.admin.validation.controller;

import com.xiushang.admin.validation.vo.ParamVo;
import com.xiushang.framework.log.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Api(tags = {"常用接口"})
@Controller
@RequestMapping(value = "/api/valid",
        produces = "application/json; charset=UTF-8")
public class ValidateController {

	@ApiOperation(value = "测试参数校验")
    @PostMapping(value = "/testNull")
	@ResponseBody
    public CommonResult val(@Valid @RequestBody ParamVo paramVo) {

        return CommonResult.success();
    }

}
