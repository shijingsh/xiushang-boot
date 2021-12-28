package com.xiushang.dubbo.service;


import com.xiushang.framework.log.CommonResult;
import org.apache.dubbo.config.annotation.DubboService;

import java.io.Serializable;


@DubboService()
public class OrderDubboSericeImpl implements OrderDubboService, Serializable {

    @Override
    public CommonResult<String> getHelloWord() {
        return CommonResult.success("hello world");
    }
}
