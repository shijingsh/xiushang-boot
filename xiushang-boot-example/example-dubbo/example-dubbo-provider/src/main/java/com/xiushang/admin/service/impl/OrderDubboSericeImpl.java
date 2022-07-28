package com.xiushang.admin.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.xiushang.admin.service.OrderPayDubboService;
import com.xiushang.dubbo.service.OrderDubboService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

import java.io.Serializable;

/**
 * 测试 userInfo 传参
 */
@DubboService(filter = "userFilter")
public class OrderDubboSericeImpl implements OrderDubboService, Serializable {

    @DubboReference
    OrderPayDubboService orderPayDubboService;

    @Override
    public JSONObject getHelloWord() {
        JSONObject object = new JSONObject();
        object.put("code",0);
        object.put("msg","hello world");

        String getClientAttachment = RpcContext.getClientAttachment().getAttachment("getClientAttachment");
        String getServerAttachment = RpcContext.getServerAttachment().getAttachment("getServerAttachment");
        System.out.println(getClientAttachment + "=========filter");
        System.out.println(getServerAttachment + "=========filter");


        orderPayDubboService.toPay();

        return object;
    }
}
