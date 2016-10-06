package com.appc.views.exception;

public class ViewException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	String code;

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ViewException() {
	}

	public ViewException(int code, String message) {
		super(message);
	}

	public ViewException(String message, Throwable cause) {
		super(message, cause);
	}

	public ViewException(String message) {
		super(message);
	}

	public ViewException(Throwable cause) {
		super(cause);
	}
}
