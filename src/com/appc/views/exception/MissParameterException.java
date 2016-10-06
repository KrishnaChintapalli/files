package com.appc.views.exception;

public class MissParameterException extends ViewException {
	private static final long serialVersionUID = 1L;
	private String missParameter;

	public String getMissParameter() {
		return this.missParameter;
	}

	public void setMissParameter(String missParameter) {
		this.missParameter = missParameter;
	}

	public MissParameterException() {
	}

	public MissParameterException(String message) {
		super(message);
	}

	public MissParameterException(String message, String missParameter) {
		super(message);
		this.missParameter = missParameter;
	}

	public MissParameterException(int code, String message, String missParameter) {
		super(code, message);
		this.missParameter = missParameter;
	}
}