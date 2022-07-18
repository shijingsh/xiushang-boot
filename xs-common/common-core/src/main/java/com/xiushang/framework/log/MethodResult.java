package com.xiushang.framework.log;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiushang.common.intf.Response;
import io.swagger.annotations.ApiModelProperty;

/**
 * 函数返回状态结果
 * 此类不返回给前端
 */
public class MethodResult<T> implements java.io.Serializable{

    public static int SUCCESS = 0;
    public static int ERROR = 1;
    /**
     * 响应编码 code:0为成功 其他值为失败
     */
    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false, deserialize = false)
    private int code = SUCCESS;
    /**
     * 错误提示
     */
    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false, deserialize = false)
    private String message = "";

    /**
     * 是否执行成功
     */
    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false, deserialize = false)
    private Boolean success = true;

    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false, deserialize = false)
    private T data;

    public MethodResult(T data) {
        this.data = data;
    }

    public MethodResult(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 成功返回结果
     */
    public static <T> MethodResult<T> success() {
        return new MethodResult<T>("成功",null);
    }
    /**
     * 成功返回结果
     */
    public static <T> MethodResult<T> success(T data) {
        return new MethodResult<T>(data);
    }

    /**
     * 成功返回结果
     */
    public static <T> MethodResult<T> success(Response<T> response) {

        MethodResult methodResult = new MethodResult<T>( response.message(),response.data());
        int code = response.code();
        if(code==SUCCESS){
            methodResult.setSuccess(true);
            methodResult.setCode(SUCCESS);
        }else {
            methodResult.setCode(ERROR);
            methodResult.setSuccess(false);
            methodResult.setCode(code);
        }

        return methodResult;
    }
    /**
     * 失败返回结果
     */
    public static <T> MethodResult<T> error() {
        MethodResult methodResult = new MethodResult<T>( "操作失败！",null);
        methodResult.setSuccess(false);
        methodResult.setCode(ERROR);
        return methodResult;
    }
    /**
     * 失败返回结果
     */
    public static <T> MethodResult<T> error(String message) {
        MethodResult methodResult = new MethodResult<T>( message,null);
        methodResult.setSuccess(false);
        methodResult.setCode(ERROR);
        return methodResult;
    }

    /**
     * 失败返回结果
     */
    public static <T> MethodResult<T> error(int code,String message) {
        MethodResult methodResult = new MethodResult<T>( message,null);
        methodResult.setSuccess(false);
        if(code==0){
            methodResult.setCode(ERROR);
        }else {
            methodResult.setCode(code);
        }

        return methodResult;
    }

}
