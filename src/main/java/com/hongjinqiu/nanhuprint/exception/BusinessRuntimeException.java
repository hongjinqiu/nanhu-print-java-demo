package com.hongjinqiu.nanhuprint.exception;

/**
 * 异常包装类
 * @author hongjinqiu 2023.05.15
 */
public class BusinessRuntimeException extends RuntimeException {
	private static final long serialVersionUID = -6448531730347863278L;

	public BusinessRuntimeException() {
		super();
	}

	public BusinessRuntimeException(String message) {
		super(message);
	}

	public BusinessRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessRuntimeException(Throwable cause) {
		super(cause);
	}
}
