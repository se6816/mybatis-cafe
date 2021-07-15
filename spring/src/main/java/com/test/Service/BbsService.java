package com.test.Service;

import java.util.List;

import com.test.domain.BbsListVO;
import com.test.domain.BbsVO;
import com.test.domain.BoardType;
import com.test.domain.Page;
import com.test.domain.PageCriteria;
import com.test.domain.UserVO;
import com.test.domain.userBbsVO;
import com.test.domain.userReplyVO;
import com.test.dto.writeRequestDto;

public interface BbsService {
	public void write(writeRequestDto wrd,BoardType boardType) throws Exception; // 글 작성
	public BbsVO read(int bid,BoardType boardType) throws Exception; // 글 상세조회
	public void modify(writeRequestDto wrd,BoardType boardType) throws Exception; // 글 수정
	public void remove(int bid, BoardType boardType) throws Exception; //  글 삭제
	public List<BbsListVO> listCriteria(PageCriteria PageCria, BoardType boardType) throws Exception; // 글 목록조회
	public int countData(Page pageCria, BoardType boardType) throws Exception;    // 글 갯수
	public List<BbsVO> hotShowArticle(PageCriteria pageCria, BoardType boardType) throws Exception; // 인기 게시글
	public List<BbsVO> todayShowArticle(PageCriteria pageCria, BoardType boardType) throws Exception;   // 오늘의 게시글
	public int clicklovers(int bid,String id,BoardType boardType);    // 좋아요 컨트롤
	public boolean isClickLovers(int bid,String id,BoardType boardType); // 좋아요를 눌렀는가?
	
	public int userCountData(String id) throws Exception;
	public int userReplyCountData(String id) throws Exception;
	public List<userBbsVO> userListCriteria(Page page,String id) throws Exception;

	public List<userReplyVO> userReplyListCriteria(Page page,String id) throws Exception;
}
