package com.xiushang.common.subscribe.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.xiushang.common.annotations.XiushangApi;
import com.xiushang.common.job.vo.SubscribeMsgAppointVo;
import com.xiushang.common.service.DynamicTaskService;
import com.xiushang.framework.log.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "微信接口")
@Controller
@ApiSort(value = 3)
@RequestMapping(value = "/api/wx/subscribe",
        produces = "application/json; charset=UTF-8")
@Validated
public class SubscribeController {

    @Autowired
    private DynamicTaskService dynamicTaskService;
    /**
     * 获取订阅
     * 0 未订阅
     * 1 已订阅
     */
    @ApiOperation(value = "查看是否已订阅消息")
    @XiushangApi
    @ResponseBody
    @GetMapping("/getSubscribeStatus")
    public CommonResult<Integer> getSubscribeStatus(@ApiParam(value = "订阅对象id",required = true) String subscribeObjectId) {
        Integer entity = dynamicTaskService.getSubscribeStatus(subscribeObjectId);

        return CommonResult.success(entity);
    }

    /**
     * 保存
     * @return
     */
    @ApiOperation(value = "取消订阅消息")
    @XiushangApi
    @ResponseBody
    @GetMapping("/cancel")
    public CommonResult<String> cancel(@ApiParam(value = "订阅对象id",required = true) String subscribeObjectId) {

        dynamicTaskService.appointCancel(subscribeObjectId);
        return CommonResult.success();
    }

    @ApiOperation("订阅消息")
    @PostMapping("/appoint")
    @XiushangApi
    @ResponseBody
    public CommonResult<Boolean> appoint(@Valid @RequestBody SubscribeMsgAppointVo appointVo) {
        Boolean flag = dynamicTaskService.appoint(appointVo);

        return CommonResult.success(flag);
    }


}
