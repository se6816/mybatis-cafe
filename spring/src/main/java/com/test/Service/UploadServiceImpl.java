package com.test.Service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.Mapper.FileMapper;
import com.test.domain.BoardType;
import com.test.domain.FileVO;

@Service
public class UploadServiceImpl implements UploadService {

	private final FileMapper FileMapper;
	@Autowired
	public UploadServiceImpl(FileMapper fileMapper) {
		this.FileMapper = fileMapper;
	}
	
	@Override
	public void save(FileVO file, BoardType boardType) {
		FileMapper.upload(file, boardType);
		 
	}
	@Override
	public FileVO selectFile(int fno,BoardType boardType) {
		return FileMapper.selectFile(fno,boardType);
	}

	@Override
	public void registerBidAll(int[] fid, int bid, BoardType boardType) {
		FileMapper.registerBidAll(fid, bid, boardType);
	}

	@Override
	public List<Integer> findContainedFile(int bid, BoardType boardType) {
		
		return FileMapper.findContainedFile(bid, boardType);
	}

	@Override
	public FileVO searchFile(int fno, BoardType boardType) {
		// TODO Auto-generated method stub
		return FileMapper.searchFile(fno, boardType);
	}

}
