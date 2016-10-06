package com.appc.file.dao;

import com.appc.file.modle.TabUser;

public abstract interface TabUserMapper {
	public abstract TabUser getUserByName(String paramString);
}
