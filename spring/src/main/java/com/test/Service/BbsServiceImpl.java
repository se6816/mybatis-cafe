package com.test.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.Mapper.BbsMapper;
import com.test.Mapper.FileMapper;
import com.test.Utils.writeUtils;
import com.test.aop.LogAspect;
import com.test.domain.BbsListVO;
import com.test.domain.BbsVO;
import com.test.domain.BoardType;
import com.test.domain.Page;
import com.test.domain.PageCriteria;
import com.test.domain.UserVO;
import com.test.domain.userBbsVO;
import com.test.domain.userReplyVO;
import com.test.dto.writeRequestDto;
/**
 * 게시판 관련 기능 구현체이다.
 * 2021.12.20
 * @author user
 *
 */
@Service
public class BbsServiceImpl implements BbsService {
	private final Logger logger = LoggerFactory.getLogger(BbsServiceImpl.class);
	/**
	 * 게시판 관련 mapper
	 */
	private final BbsMapper BbsMapper;
	
	/**
	 * DB에 파일 정보 저장 관련 mapper 
	 */
	private final FileMapper FileMapper;
	
	@Autowired
	private writeUtils wut;
	@Autowired
	public BbsServiceImpl(BbsMapper bbsMapper, FileMapper fileMapper) {
		BbsMapper = bbsMapper;
		FileMapper= fileMapper;
	}
	
	/**
	 * 글을 작성한다.
	 * @param wrd 글작성 Dto
	 * @param boardType 게시판 종류 
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void write(writeRequestDto wrd,BoardType boardType) throws Exception {
		writeUtils writeUtils= new writeUtils(BbsMapper,FileMapper);
		writeUtils.writeAndUpload(wrd, boardType);	
	}
	
	/**
	 * 특정 글을 읽는다.
	 * @param bid 글 번호
	 * @param boardType 게시판 종류 
	 * @return 특정 글
	 */
	@Override
	public BbsVO read(int bid,BoardType boardType) throws Exception {
		logger.info("dd");
		BbsVO bbs= BbsMapper.read(bid,boardType);
		BbsMapper.Bhit(bid, boardType);
		return bbs;
	}
	
	/**
	 * 글을 수정한다.
	 * @param wrd 글작성 Dto
	 * @param boardType 게시판 종류 
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void modify(writeRequestDto wrd, BoardType boardType)  throws Exception {
		writeUtils writeUtils= new writeUtils(BbsMapper,FileMapper);
		writeUtils.modifyAndUpload(wrd, boardType);
	}
	
	/**
	 * 글을 삭제한다.
	 * @param bid 글 번호
	 * @param boardType 게시판 종류 
	 * 
	 */
	@Override
	public void remove(int bid,BoardType boardType) throws Exception {
		BbsMapper.delete(bid,boardType);
	}
	
	/**
	 * 글 목록을 가져온다.
	 * @param pageCria 페이지와 검색 정보
	 * @param boardType 게시판 종류
	 * @return 글 목록
	 */
	@Override
	public List<BbsListVO> listCriteria(PageCriteria pageCria,BoardType boardType) throws Exception {
		return BbsMapper.listCriteria(pageCria,boardType);
		
	}
	
	/**
	 * 글의 수를 가져온다.
	 * @param pageCria 페이지 정보
	 * @param boardType 게시판 종류
	 * @return 글의 총 개수
	 */
	@Override
	public int countData(Page pageCria,BoardType boardType) throws Exception {
		return BbsMapper.countData(pageCria, boardType);
	}
	
	/**
	 * 7일 내에 가장 많이 조회된 상위 10개를 조회한다.
	 * @param pageCria 페이지 정보
	 * @param boardType 게시판 종류
	 * @return 7일 내에 가장 많이 조회된 상위 10개
	 */
	@Override
	public List<BbsVO> hotShowArticle(PageCriteria pageCria, BoardType boardType) throws Exception {
		return BbsMapper.hotArticle(pageCria, boardType);
		
	}
	
	/**
	 * 하루 동안 가장 많이 조회된 상위 10개를 조회한다.
	 * @param pageCria 페이지 정보
	 * @param boardType 게시판 종류
	 * @return 하루 동안 가장 많이 조회된 상위 10개
	 */
	@Override
	public List<BbsVO> todayShowArticle(PageCriteria pageCria, BoardType boardType) throws Exception {
		return BbsMapper.todayArticle(pageCria, boardType);
		
	}

	
	/**
	 * 좋아요 기능이다.
	 * @param bid 글 번호
	 * @param id 계정 아이디
	 * @param boardType 게시판 종류
	 * @return value가 0이면 기존에 좋아요를 누르지 않음 
	 */
	@Override
	public void clicklovers(int bid, String id, BoardType boardType) {
		BbsMapper.addClickLovers(bid, id, boardType);
	}

	/**
	 * 좋아요를 누른 적이 있는지 알 수 있다.
	 * @param bid 글 번호
	 * @param id 계정 아이디
	 * @param boardType 게시판 종류
	 * @return true일시 누른 적이 있다.
	 */
	@Override
	public boolean isClickLovers(int bid, String id, BoardType boardType) {
		int value=BbsMapper.isClickLovers(bid, id, boardType);
		if(value>0) return true;
		
		return false;
	}

	/**
	 * 유저가 작성한 글목록을 조회한다.
	 * @param page 페이지 정보
	 * @param id 아이디
	 * @return 유저가 작성한 글목록
	 */
	@Override
	public List<userBbsVO> userListCriteria(Page page, String id) throws Exception {
		// TODO Auto-generated method stub
		return BbsMapper.userListCriteria(page, id);
	}

	/**
	 * 유저가 작성한 댓글 목록을 조회한다.
	 * @param page 페이지 정보
	 * @param id 아이디
	 * @return 유저가 작성한 댓글 목록
	 */
	@Override
	public List<userReplyVO> userReplyListCriteria(Page page, String id) throws Exception {
		// TODO Auto-generated method stub
		return BbsMapper.userReplyListCriteria(page, id);
	}

	/**
	 * 유저가 작성한 글의 수를 계산한다.
	 * @param id 아이디
	 * @return 유저가 작성한 글의 수
	 */
	@Override
	public int userCountData(String id) throws Exception {
		// TODO Auto-generated method stub
		return BbsMapper.userCountData(id);
	}

	/**
	 * 유저가 작성한 댓글 수를 계산한다.
	 * @param id 아이디
	 * @return 유저가 작성한 댓글 수
	 */
	@Override
	public int userReplyCountData(String id) throws Exception {
		return BbsMapper.userReplyCountData(id);
	}

}
