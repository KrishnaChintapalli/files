package com.appc.file.modle;

import com.appc.model.BaseModel;

public class TabUser extends BaseModel {
	private static final long serialVersionUID = 1L;
	private String id;
	private String username;
	private String pwd;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}