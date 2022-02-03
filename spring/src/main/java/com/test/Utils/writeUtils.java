package com.test.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.Mapper.BbsMapper;
import com.test.Mapper.FileMapper;
import com.test.Service.BbsService;
import com.test.Service.UploadService;
import com.test.domain.BbsVO;
import com.test.domain.BoardType;
import com.test.dto.writeRequestDto;

/**
 * 글 작성 관련 Util
 * 2021.12.21
 * @author user
 *
 */
@Service
public class writeUtils {
	/**
	 * 게시판 관련 mapper
	 */
	private final BbsMapper BbsMapper;
	
	/**
	 * 파일 관련 mapper
	 */
	private final FileMapper FileMapper;
	
	@Autowired
	public writeUtils(BbsMapper bbsMapper, FileMapper fileMapper) {
		BbsMapper = bbsMapper;
		FileMapper = fileMapper;
	}
	
	/**
	 * 새 글 작성 기능이다.
	 * @param wrd  글 작성 양식 Dto
	 * @param boardType 게시판 종류
	 * @throws Exception
	 */
	public void writeAndUpload(writeRequestDto wrd, BoardType boardType) throws Exception {
		BbsVO bvo= wrd.makeBbsVO();
		
		BbsMapper.insert(bvo, boardType);
		
		// 글과 연결될 파일이 있다면 
		if(wrd.existFile()) {
			FileMapper.registerBidAll(wrd.getFid(), bvo.getBid(), boardType);
		}
	}
	
	/**
	 * 글 수정 기능이다.
	 * @param wrd  글 작성 양식 Dto
	 * @param boardType 게시판 종류
	 * @throws Exception
	 */
	public void modifyAndUpload(writeRequestDto wrd, BoardType boardType) throws Exception {
		BbsVO bvo= wrd.makeModifyBbsVO();
		
		
		HashSet<Integer> hash= new HashSet<>();                                           // 수정된 글과 연결될 파일들을 담는 곳
		List<Integer> list=FileMapper.findContainedFile(bvo.getBid(), boardType);         // DB에 글과 연결된 파일들을 가져온다.
		
		// 수정한 글과 연결된 파일이 존재한다면
		if(wrd.existFile()) {
			for(int item : wrd.getFid()) {
				hash.add(item);                                                  // 현재 수정된 글에 포함된 파일 번호들
			}
		}
		
		// 수정된 특정 글과 연결될 파일 번호들과 특정 글과 연결된 DB에서 가져온 파일 번호를 비교하여
		// 수정됨으로써 제외된 파일 번호를 찾는다.
		int[] delId=list.stream()
					.filter(number -> !hash.contains(number))
					.mapToInt(Integer::intValue)
					.toArray();
		
		// 수정한 글과 연결된 파일이 존재한다면 
		if(wrd.existFile()) {
			FileMapper.registerBidAll(wrd.getFid(), bvo.getBid(), boardType);
		}
		
		BbsMapper.update(bvo, boardType);
		
		// 글이 수정되어 DB에서 글과 연결 해제해야할 글이 있다면 
		if(delId.length>0) { 
			FileMapper.deleteFileAll(delId, boardType);
		}
		
	}
}
