package com.appc.file.service.impl;

import com.appc.file.dao.TabFileMapper;
import com.appc.file.modle.TabFile;
import com.appc.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("fileService")
public class FileServiceImpl implements FileService {

	@Autowired
	private TabFileMapper fileMapper;

	public void addFile(TabFile file) {
		this.fileMapper.insert(file);
	}

	public TabFile getFile(String fileId) {
		return this.fileMapper.selectByPrimaryKey(fileId);
	}
}