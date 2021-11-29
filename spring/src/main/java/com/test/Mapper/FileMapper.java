package com.test.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.test.domain.BoardType;
import com.test.domain.FileVO;

public interface FileMapper {
	public void upload(@Param("file") FileVO file,@Param("boardType")BoardType boardType);
	public FileVO searchFile(@Param("fid") int fid,@Param("boardType")BoardType boardType);
	public FileVO selectFile(@Param("fid") int fid,@Param("boardType")BoardType boardType);
	public List<Integer> findContainedFile(@Param("bid") int bid,@Param("boardType")BoardType boardType);
	public void registerBidAll(@Param("fid") int[] fid,@Param("bid") int bid,@Param("boardType")BoardType boardType);
	public void deleteFileAll(@Param("fid") int[] fid,@Param("boardType")BoardType boardType);
	
}

