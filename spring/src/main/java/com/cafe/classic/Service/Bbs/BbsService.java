package com.cafe.classic.Service.Bbs;

import java.util.List;
import java.util.Optional;

import com.cafe.classic.domain.BbsListVO;
import com.cafe.classic.domain.BbsVO;
import com.cafe.classic.domain.BoardType;
import com.cafe.classic.domain.Category;
import com.cafe.classic.domain.Page;
import com.cafe.classic.domain.PageCriteria;
import com.cafe.classic.domain.UserVO;
import com.cafe.classic.domain.UserBbsVO;
import com.cafe.classic.domain.UserReplyVO;
import com.cafe.classic.dto.writeRequestDto;

/**
 * 게시판 Service interface
 * 2021.12.20
 * @author user
 *
 */
public interface BbsService {
	public void write(writeRequestDto wrd,Category boardType); // 글 작성
	public BbsVO read(int bid,Category boardType); // 글 상세조회
	public void modify(writeRequestDto wrd,Category boardType); // 글 수정
	public void remove(int bid, Category boardType); //  글 삭제
	public List<BbsListVO> listCriteria(PageCriteria PageCria, Category boardType); // 글 목록조회
	public int countData(Page pageCria, Category boardType);    // 글 갯수
	public List<BbsVO> hotShowArticle(PageCriteria pageCria, BoardType boardType); // 인기 게시글
	public void clicklovers(int bid,String id,BoardType boardType);    // 좋아요 컨트롤
	public boolean isClickLovers(int bid,String id,BoardType boardType); // 좋아요를 눌렀는가?
	
	public int userCountData(String id);
	public int userReplyCountData(String id);
	public List<UserBbsVO> userListCriteria(Page page,String id);

	public List<UserReplyVO> userReplyListCriteria(Page page,String id);
}
