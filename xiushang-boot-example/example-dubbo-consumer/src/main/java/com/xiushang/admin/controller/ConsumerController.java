package com.xiushang.admin.controller;

import com.xiushang.dubbo.service.OrderDubboService;
import com.xiushang.framework.log.CommonResult;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping("/test")
public class ConsumerController {
    @DubboReference
    OrderDubboService orderDubboService;

    @GetMapping("getOrder")
    public CommonResult<String> getOrder() {
        return orderDubboService.getHelloWord();
    }
}
