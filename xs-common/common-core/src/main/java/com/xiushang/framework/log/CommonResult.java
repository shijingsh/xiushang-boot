package com.xiushang.framework.log;

import com.xiushang.common.intf.Response;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 封装接口相应对象
 */
@ApiModel
public class CommonResult<T> implements java.io.Serializable{
	public static int SUCCESS = 0;
	public static int ERROR = 1;
	//错误代码
	@ApiModelProperty(value = "响应编码 code:0为成功 其他值为失败", name = "errorCode")
	private int errorCode;
	//错误提示
	@ApiModelProperty(value = "错误消息", name = "code")
    private String errorText;

    //成功时的提示
	@ApiModelProperty(value = "成功消息")
    private String successText;
	@ApiModelProperty(value = "是否执行成功")
    private Boolean execResult = true;

    //返回对象
	@ApiModelProperty(value = "响应数据对象", name = "data")
    private T data;

    public CommonResult() {
    }

    public CommonResult(int errorCode, String message, T data){
    	this.errorCode = errorCode;
    	if(errorCode == SUCCESS){
			this.successText = message;
		}else {
			this.errorText = message;
		}

    	this.data = data;
    }

	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorText() {
		return errorText;
	}
	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

    public String getSuccessText() {
		return successText;
	}

	public void setSuccessText(String successText) {
		this.successText = successText;
	}


	public Boolean getExecResult() {
		return execResult;
	}

	public void setExecResult(Boolean execResult) {
		this.execResult = execResult;
	}

	/**
	 * 成功返回结果
	 */
	public static <T> CommonResult<T> success() {
		return new CommonResult<T>(SUCCESS, "成功",null);
	}
	/**
	 * 成功返回结果
	 */
	public static <T> CommonResult<T> success(T data) {
		return new CommonResult<T>(SUCCESS, "成功", data);
	}

	/**
	 * 成功返回结果
	 */
	public static <T> CommonResult<T> success(MethodResult<T> methodResult) {
		if(!methodResult.getSuccess()){
			int code = methodResult.getCode();
			if(code==SUCCESS){
				code = ERROR;
			}
			return CommonResult.error(code,methodResult.getMessage());
		}
		return new CommonResult<T>(SUCCESS, "成功", methodResult.getData());
	}


	/**
	 * 成功返回结果
	 */
	public static <T> CommonResult<T> success(Response<T> response) {
		int code = response.code();
		if(code!=SUCCESS){

			return CommonResult.error(code,response.message());
		}
		return new CommonResult<T>(SUCCESS, "成功", response.data());
	}

	/**
	 * 失败返回结果
	 */
	public static <T> CommonResult<T> error(String message) {
		return new CommonResult<T>(ERROR, message,null);
	}
	/**
	 * 失败返回结果
	 */
	public static <T> CommonResult<T> error(int errorCode, String message) {
		return new CommonResult<T>(errorCode, message,null);
	}

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResponseBody{");
        sb.append("errorCode=").append(errorCode);
        sb.append(", errorText='").append(errorText).append('\'');
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
