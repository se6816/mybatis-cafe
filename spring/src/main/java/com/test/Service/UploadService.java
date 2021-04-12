package com.test.Service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.test.domain.BoardType;
import com.test.domain.FileVO;

public interface UploadService {
	public void save(FileVO file, BoardType boardType);
	public FileVO searchFile(int fno,BoardType boardType);
	public FileVO selectFile(int fno,BoardType boardType);
	public List<Integer> findContainedFile(int bid,BoardType boardType);
	public void registerBidAll(int[] fid, int bid,BoardType boardType);

	
}
