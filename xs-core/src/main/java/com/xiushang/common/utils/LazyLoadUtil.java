package com.xiushang.common.utils;

import com.alibaba.fastjson.JSON;
import org.hibernate.Hibernate;

public class LazyLoadUtil {

    public synchronized static void fullLoad(Object object) {
        if(object == null)
            return;
        JSON.toJSONString(object, new LazyLoadFilter());
    }

    public  static void load(Object proxy) {
        if(proxy == null)
            return;
        Hibernate.initialize(proxy);
    }

}
