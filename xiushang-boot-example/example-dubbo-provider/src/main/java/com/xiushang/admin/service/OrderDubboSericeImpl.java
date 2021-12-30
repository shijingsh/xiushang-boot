package com.xiushang.admin.service;


import com.alibaba.fastjson.JSONObject;
import com.xiushang.dubbo.service.OrderDubboService;
import org.apache.dubbo.config.annotation.DubboService;

import java.io.Serializable;


@DubboService()
public class OrderDubboSericeImpl implements OrderDubboService, Serializable {

    @Override
    public JSONObject getHelloWord() {
        JSONObject object = new JSONObject();
        object.put("code",0);
        object.put("msg","hello world");

        return object;
    }
}
