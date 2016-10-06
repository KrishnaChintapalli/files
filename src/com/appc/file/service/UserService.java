package com.appc.file.service;

import com.appc.file.modle.TabUser;

public abstract interface UserService {
	public abstract TabUser validateUser(TabUser paramTabUser);
}