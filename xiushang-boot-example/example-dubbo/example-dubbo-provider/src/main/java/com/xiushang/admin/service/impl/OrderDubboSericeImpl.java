package com.xiushang.admin.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.xiushang.admin.service.OrderPayDubboService;
import com.xiushang.dubbo.service.OrderDubboService;
import com.xiushang.framework.log.SecurityConstants;
import com.xiushang.framework.utils.UserHolder;
import com.xiushang.security.SecurityUser;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

import java.io.Serializable;

/**
 * 测试 userInfo 传参
 */
@DubboService(filter = "userFilter",retries = 0)
public class OrderDubboSericeImpl implements OrderDubboService, Serializable {

    @DubboReference
    OrderPayDubboService orderPayDubboService;

    @Override
    public JSONObject getHelloWord() {
        JSONObject object = new JSONObject();
        object.put("code",0);
        object.put("msg","hello world");

        SecurityUser user = UserHolder.get();
        System.out.println("=========getHelloWord=========");
        System.out.println(user);


        orderPayDubboService.toPay();

        return object;
    }
}
