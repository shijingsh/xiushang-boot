package com.xiushang.marketing.oceanengine.api.bean;

import com.alibaba.fastjson.JSON;

/**
 *
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
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
