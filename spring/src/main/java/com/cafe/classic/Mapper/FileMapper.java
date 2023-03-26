package com.cafe.classic.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cafe.classic.domain.BoardType;
import com.cafe.classic.domain.Category;
import com.cafe.classic.domain.FileVO;

@Mapper
public interface FileMapper {
	public void upload(@Param("file") FileVO file,@Param("boardType")Category boardType);
	public FileVO searchFile(@Param("fid") int fid,@Param("boardType")Category boardType);
	public FileVO selectFile(@Param("fid") int fid,@Param("boardType")Category boardType);
	public List<Integer> findContainedFile(@Param("bid") int bid,@Param("boardType")Category boardType);
	public void registerBidAll(@Param("fid") int[] fid,@Param("bid") int bid,@Param("boardType")Category boardType);
	public void deleteFileAll(@Param("fid") int[] fid,@Param("boardType")Category boardType);
	
}

