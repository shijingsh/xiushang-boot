package com.xiushang.admin.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.xiushang.admin.service.OrderPayDubboService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

import java.io.Serializable;


@DubboService(filter = "userFilter")
public class OrderPayDubboSericeImpl implements OrderPayDubboService, Serializable {

    @Override
    public JSONObject toPay() {
        JSONObject object = new JSONObject();
        object.put("code",0);
        object.put("msg","to pay");

        String getClientAttachment = RpcContext.getClientAttachment().getAttachment("getClientAttachment");
        String getServerAttachment = RpcContext.getServerAttachment().getAttachment("getServerAttachment");
        System.out.println(getClientAttachment + "=========filter2");
        System.out.println(getServerAttachment + "=========filter2");
        return object;
    }
}
