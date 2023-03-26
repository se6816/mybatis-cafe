package com.cafe.classic.Service.file;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe.classic.Mapper.FileMapper;
import com.cafe.classic.domain.BoardType;
import com.cafe.classic.domain.Category;
import com.cafe.classic.domain.FileVO;
/**
 * 파일 업로드 관련 구현체
 * @author user
 *
 */
@Service
public class UploadServiceImpl implements UploadService {

	/**
	 * 파일 업로드 관련 mapper
	 */
	private final FileMapper FileMapper;
	
	@Autowired
	public UploadServiceImpl(FileMapper fileMapper) {
		this.FileMapper = fileMapper;
	}
	
	/**
	 * @param file 파일 정보
	 * @param boardType 게시판 종류
	 * 
	 */
	@Override
	public void save(FileVO file, Category boardType) {
		FileMapper.upload(file, boardType);
		 
	}
	
	/**
	 * 파일 하나의 정보를 가져온다.
	 * @param fno 파일 번호
	 * @param boardType 게시판 종류
	 * @return 파일 정보
	 */
	@Override
	public FileVO selectFile(int fno,Category boardType) {
		return FileMapper.selectFile(fno,boardType);
	}
	
	/**
	 * 파일과 글을 연결한다.
	 * @param fid 파일 번호들
	 * @param bid 글 번호
	 * @param boardType 게시판 종류
	 */
	@Override
	public void registerBidAll(int[] fid, int bid, Category boardType) {
		FileMapper.registerBidAll(fid, bid, boardType);
	}
	
	/**
	 * 글과 연결된 파일 정보들을 조회한다.
	 * @param bid 글 번호
	 * @param boardType 게시판 종류
	 * @return 글과 연결된 파일 정보들
	 */
	@Override
	public List<Integer> findContainedFile(int bid, Category boardType) {
		
		return FileMapper.findContainedFile(bid, boardType);
	}

	/**
	 * 다운로드를 진행하기 위해 해당 파일 정보를 불러온다.
	 * @param fno 파일 번호
	 * @param boardType 게시판 정보
	 * @return 파일 정보
	 */
	@Override
	public FileVO searchFile(int fno, Category boardType) {
		return FileMapper.searchFile(fno, boardType);
	}

}
