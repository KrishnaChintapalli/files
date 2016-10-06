package com.appc.file.dao;

import com.appc.file.modle.TabFile;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface TabFileMapper {
	public abstract int deleteByPrimaryKey(String paramString);

	public abstract int insert(TabFile paramTabFile);

	public abstract TabFile selectByPrimaryKey(String paramString);
}