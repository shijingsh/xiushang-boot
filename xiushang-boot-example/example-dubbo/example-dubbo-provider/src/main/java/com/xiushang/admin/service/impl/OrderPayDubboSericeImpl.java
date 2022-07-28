package com.xiushang.admin.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.xiushang.admin.service.OrderPayDubboService;
import com.xiushang.framework.utils.UserHolder;
import com.xiushang.security.SecurityUser;
import org.apache.dubbo.config.annotation.DubboService;

import java.io.Serializable;


@DubboService(filter = "userFilter",retries = 0)
public class OrderPayDubboSericeImpl implements OrderPayDubboService, Serializable {

    @Override
    public JSONObject toPay() {
        JSONObject object = new JSONObject();
        object.put("code",0);
        object.put("msg","to pay");

        SecurityUser user = UserHolder.get();
        System.out.println("=========toPay=========");
        System.out.println(user);

        return object;
    }
}
