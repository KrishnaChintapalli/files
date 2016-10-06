package com.appc.views.exception;

public class ViewNotFoundException extends ViewException {
	private static final long serialVersionUID = 1L;
	private String notFoundParameter;

	public ViewNotFoundException() {
	}

	public ViewNotFoundException(String notFoundParameter) {
		this.notFoundParameter = notFoundParameter;
	}

	public ViewNotFoundException(String message, Throwable cause, String notFoundParameter) {
		super(message, cause);
		this.notFoundParameter = notFoundParameter;
	}

	public ViewNotFoundException(String message, String notFoundParameter) {
		super(message);
		this.notFoundParameter = notFoundParameter;
	}

	public ViewNotFoundException(Throwable cause, String notFoundParameter) {
		super(cause);
		this.notFoundParameter = notFoundParameter;
	}

	public String getNotFoundParameter() {
		return this.notFoundParameter;
	}

	public void setNotFoundParameter(String notFoundParameter) {
		this.notFoundParameter = notFoundParameter;
	}
}