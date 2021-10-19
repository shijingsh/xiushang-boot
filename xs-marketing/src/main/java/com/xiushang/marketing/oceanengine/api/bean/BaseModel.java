package com.xiushang.marketing.oceanengine.api.bean;

import com.alibaba.fastjson.JSON;

/**
 *

 */
public class BaseModel {
    /**
     * Returns a JSON string corresponding to object state
     *
     * @return JSON representation
     */
    public String toJSON() {
        return JSON.toJSONString(this);
    }

    @Override
    public String toString() {
        return toJSON();
    }
}
