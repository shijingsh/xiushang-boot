package com.xiushang.admin.service;


import com.xiushang.dubbo.service.OrderDubboService;
import com.xiushang.framework.log.CommonResult;
import org.apache.dubbo.config.annotation.DubboService;

import java.io.Serializable;


@DubboService()
public class OrderDubboSericeImpl implements OrderDubboService, Serializable {

    @Override
    public String getHelloWord() {
        return "hello world";
    }
}
