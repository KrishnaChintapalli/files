package com.appc.views.exception;

public class ViewServiceException extends ViewException {
	private static final long serialVersionUID = 1L;
	private int serviceErrorCode;

	public int getServiceErrorCode() {
		return this.serviceErrorCode;
	}

	public void setServiceErrorCode(int serviceErrorCode) {
		this.serviceErrorCode = serviceErrorCode;
	}

	public ViewServiceException() {
	}

	public ViewServiceException(int serviceErrorCode, String message, Throwable cause) {
		super(message, cause);
		this.serviceErrorCode = serviceErrorCode;
	}
}