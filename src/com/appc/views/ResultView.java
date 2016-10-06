package com.appc.views;

public class ResultView extends BaseView {
	private int status;
	private Object data;
	private String message;
	private String code;
	private Long time;

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getTime() {
		if (this.time != null) {
			return this.time;
		}
		return Long.valueOf(System.currentTimeMillis());
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public ResultView() {
	}

	public ResultView(int status) {
		this.status = status;
	}

	public ResultView(int status, Object data) {
		this.status = status;
		this.data = data;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ResultView(int status, String code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.status = status;
		this.data = data;
	}
}