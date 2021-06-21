package com.xiushang.common.exception;

/**
 * 抛出运行时异常
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = -8712347542233849665L;

	public ServiceException(){
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(Throwable cause){
		super(cause);
	}

	/**
	 * 采用String.format方式格式化message（非MessageFormat.format方式）
	 * @param message 带“%d, %s”的字符串
	 * @param args 参数
	 */
	public ServiceException(String message, Object... args) {
		super(String.format(message, args));
	}
}
