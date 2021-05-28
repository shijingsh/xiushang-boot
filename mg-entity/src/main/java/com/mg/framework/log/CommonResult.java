package com.mg.framework.log;

import io.swagger.annotations.ApiModelProperty;

/**
 * 封装接口相应对象
 */
public class CommonResult<T> implements java.io.Serializable{
	public static int SUCCESS = 0;
	public static int ERROR = 1;
	//错误代码
	@ApiModelProperty("响应编码")
	private int errorCode;
	//错误提示
	@ApiModelProperty("错误消息")
    private String errorText;

    //成功时的提示
	@ApiModelProperty("成功消息")
    private String successText;
	@ApiModelProperty("是否执行成功")
    private boolean execResult = true;

    //返回对象
	@ApiModelProperty("响应数据对象")
    private T data;

    public CommonResult() {
    }

    public CommonResult(int errorCode, String errorText, T data){
    	this.errorCode = errorCode;
    	this.errorText = errorText;
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

	public boolean isExecResult() {
		return execResult;
	}

	public void setExecResult(boolean execResult) {
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
