package com.xiushang.marketing.oceanengine.api.bean;

import lombok.Data;

/**
 * @author <a href="mailto:gy1zc3@gmail.com">zacky</a>
 */
@Data
public class OceanEngineResponse<T> extends BaseModel {
    private String request_id = "";
    private int code;
    private String message;
    private T data;
}
