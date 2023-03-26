package com.cafe.classic.Service.reply;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cafe.classic.config.auth.PrincipalDetails;
import com.cafe.classic.domain.BoardType;
import com.cafe.classic.domain.ReplyVO;
import com.cafe.classic.domain.replyPageCriteria;

/**
 * 댓글 Service interface
 * 2021.12.20
 * @author user
 *
 */
public interface ReplyService {
	public ReplyVO read(String rid, BoardType boardType); // 특정 댓글 정보
	public void write(ReplyVO reply, BoardType boardType);   // 댓글 작성
	public List<ReplyVO> listCriteria(int bid,replyPageCriteria replyPageCriteria,BoardType boardType, String username); // 댓글 조회
	public void deleteReply(String rid, BoardType boardType); // 댓글 삭제
	
}
