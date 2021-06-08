package com.xiushang.common.context;

import com.xiushang.entity.InstanceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存单台服务器的一些上下文对象
 */
public class MgServerContext {
    private static Logger logger = LoggerFactory.getLogger(MgServerContext.class);

    protected static Map<String, InstanceEntity> instanceMap = new HashMap<>();

    public static Map<String, InstanceEntity> getInstanceMap() {
        return instanceMap;
    }

    public static InstanceEntity getInstance(String id) {
        if(instanceMap.containsKey(id)) {
            return instanceMap.get(id);
        }
        else {
            return null;
        }
    }

    public static void setInstance(String id,InstanceEntity instanceEntity) {
        instanceMap.put(id,instanceEntity);
    }
}
