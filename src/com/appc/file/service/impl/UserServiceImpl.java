package com.appc.file.service.impl;

import com.appc.file.dao.TabUserMapper;
import com.appc.file.modle.TabUser;
import com.appc.file.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private TabUserMapper userMapper;

	public TabUser validateUser(TabUser tuser) {
		TabUser dbUser = this.userMapper.getUserByName(tuser.getUsername());
		if ((dbUser != null) && (dbUser.getPwd().equals(tuser.getPwd()))) {
			return dbUser;
		}
		return null;
	}
}