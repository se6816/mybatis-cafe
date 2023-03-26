package com.cafe.classic.Service.Bbs;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.redis.core.ValueOperations;
import com.cafe.classic.Mapper.BbsMapper;
import com.cafe.classic.Mapper.FileMapper;
import com.cafe.classic.Utils.writeUtils;
import com.cafe.classic.aop.LogAspect;
import com.cafe.classic.domain.BbsListVO;
import com.cafe.classic.domain.BbsVO;
import com.cafe.classic.domain.BoardType;
import com.cafe.classic.domain.Category;
import com.cafe.classic.domain.Page;
import com.cafe.classic.domain.PageCriteria;
import com.cafe.classic.domain.RedisKey;
import com.cafe.classic.domain.UserVO;
import com.cafe.classic.domain.UserBbsVO;
import com.cafe.classic.domain.UserReplyVO;
import com.cafe.classic.dto.writeRequestDto;
import com.cafe.classic.exception.PageNotFoundException;
/**
 * 게시판 관련 기능 구현체이다.
 * 2021.12.20
 * @author user
 *
 */
@Service
public class BbsServiceImpl implements BbsService {
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	/**
	 * 게시판 관련 mapper
	 */
	private final BbsMapper BbsMapper;
	
	/**
	 * DB에 파일 정보 저장 관련 mapper 
	 */
	private final FileMapper FileMapper;
	private final ValueOperations<String,String> ValueOperations;
	
	
	public BbsServiceImpl(BbsMapper bbsMapper, FileMapper fileMapper,StringRedisTemplate stringRedisTemplate) {
		BbsMapper = bbsMapper;
		FileMapper = fileMapper;
		this.ValueOperations=stringRedisTemplate.opsForValue();
		
	}
	/**
	 * 글을 작성한다.
	 * @param wrd 글작성 Dto
	 * @param boardType 게시판 종류 
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void write(writeRequestDto wrd,Category boardType){
		BbsVO bvo= wrd.makeBbsVO();
		BbsMapper.insert(bvo, boardType);
		// 글과 연결될 파일이 있다면 
		if(wrd.existFile()) {
			FileMapper.registerBidAll(wrd.getFid(), bvo.getBid(), boardType);
		}
	}
	
	/**
	 * 특정 글을 읽는다.
	 * @param bid 글 번호
	 * @param boardType 게시판 종류 
	 * @return 특정 글
	 */
	@Override
	public BbsVO read(int bid,Category boardType){
		logger.info("{} 조회",bid);
		ValueOperations.setIfAbsent(RedisKey.ARTICLE_HIT_KEY+boardType.getCategoryName()+":"+bid, "0");
		ValueOperations.increment(RedisKey.ARTICLE_HIT_KEY+boardType.getCategoryName()+":"+bid);
		return Optional.ofNullable(BbsMapper.read(bid,boardType))
								.filter((b)->!b.isNotFoundPage())
								.orElseThrow(PageNotFoundException::new);
	}
	
	/**
	 * 글을 수정한다.
	 * @param wrd 글작성 Dto
	 * @param boardType 게시판 종류 
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void modify(writeRequestDto wrd, Category boardType) {
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
	
	/**
	 * 글을 삭제한다.
	 * @param bid 글 번호
	 * @param boardType 게시판 종류 
	 * 
	 */
	@Override
	public void remove(int bid,Category boardType) {
		BbsMapper.delete(bid,boardType);
	}
	
	/**
	 * 글 목록을 가져온다.
	 * @param pageCria 페이지와 검색 정보
	 * @param boardType 게시판 종류
	 * @return 글 목록
	 */
	@Override
	public List<BbsListVO> listCriteria(PageCriteria pageCria,Category boardType){
		return BbsMapper.listCriteria(pageCria,boardType);
		
	}
	
	/**
	 * 글의 수를 가져온다.
	 * @param pageCria 페이지 정보
	 * @param boardType 게시판 종류
	 * @return 글의 총 개수
	 */
	@Override
	public int countData(Page pageCria,Category boardType){
		return BbsMapper.countData(pageCria, boardType);
	}
	
	/**
	 * 7일 내에 가장 많이 조회된 상위 10개를 조회한다.
	 * @param pageCria 페이지 정보
	 * @param boardType 게시판 종류
	 * @return 7일 내에 가장 많이 조회된 상위 10개
	 */
	@Override
	public List<BbsVO> hotShowArticle(PageCriteria pageCria, BoardType boardType){
		return BbsMapper.hotArticle(pageCria, boardType);
		
	}
	/**
	 * 좋아요 기능이다.
	 * @param bid 글 번호
	 * @param id 계정 아이디
	 * @param boardType 게시판 종류
	 * @return value가 0이면 기존에 좋아요를 누르지 않음 
	 */
	@Override
	public void clicklovers(int bid, String id, BoardType boardType){
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
		return (BbsMapper.isClickLovers(bid, id, boardType)>0) ? true
															: false;
	}

	/**
	 * 유저가 작성한 글목록을 조회한다.
	 * @param page 페이지 정보
	 * @param id 아이디
	 * @return 유저가 작성한 글목록
	 */
	@Override
	public List<UserBbsVO> userListCriteria(Page page, String id) {
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
	public List<UserReplyVO> userReplyListCriteria(Page page, String id){
		// TODO Auto-generated method stub
		return BbsMapper.userReplyListCriteria(page, id);
	}

	/**
	 * 유저가 작성한 글의 수를 계산한다.
	 * @param id 아이디
	 * @return 유저가 작성한 글의 수
	 */
	@Override
	public int userCountData(String id){
		// TODO Auto-generated method stub
		return BbsMapper.userCountData(id);
	}

	/**
	 * 유저가 작성한 댓글 수를 계산한다.
	 * @param id 아이디
	 * @return 유저가 작성한 댓글 수
	 */
	@Override
	public int userReplyCountData(String id){
		return BbsMapper.userReplyCountData(id);
	}
	
	

}
