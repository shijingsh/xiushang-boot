package com.xiushang.marketing.oceanengine.api.bean;

import lombok.Data;


@Data
public class OceanEngineResponse<T> extends BaseModel {
    private String request_id = "";
    private int code;
    private String message;
    private T data;
}
