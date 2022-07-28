package com.xiushang.admin.dubbo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiushang.dubbo.service.OrderDubboService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"常用接口"})
@RestController()
@RequestMapping("/api/dbtest")
public class DubboTestController {
    @DubboReference
    OrderDubboService orderDubboService;

    @ApiOperation(value = "dbtest")
    @GetMapping("getOrder")
    public String getOrder() {
        JSONObject object = orderDubboService.getHelloWord();

        return JSON.toJSONString(object);
    }
}
