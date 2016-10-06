package com.appc.file.service;

import com.appc.file.modle.TabFile;

public abstract interface FileService {
	public abstract void addFile(TabFile paramTabFile);

	public abstract TabFile getFile(String paramString);
}