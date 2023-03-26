package com.cafe.classic.Service.file;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cafe.classic.domain.BoardType;
import com.cafe.classic.domain.Category;
import com.cafe.classic.domain.FileVO;
/**
 * 파일 업로드 관련 인터페이스
 * 2021.12.21
 * @author user
 *
 */
public interface UploadService {
	public void save(FileVO file, Category boardType);  // 파일 임시 저장
	public FileVO searchFile(int fno,Category boardType);  // 파일 검색
	public FileVO selectFile(int fno,Category boardType);   // 파일 선택
	public List<Integer> findContainedFile(int bid,Category boardType); // 글에 포함된 파일 목록 조회
	public void registerBidAll(int[] fid, int bid,Category boardType);  // 파일 정식 등록

	
}
