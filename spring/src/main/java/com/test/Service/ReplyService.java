package com.test.Service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.test.domain.BoardType;
import com.test.domain.ReplyVO;
import com.test.domain.replyPageCriteria;

/**
 * 댓글 Service interface
 * 2021.12.20
 * @author user
 *
 */
public interface ReplyService {
	public void write(ReplyVO reply, BoardType boardType) throws Exception;   // 댓글 작성
	public List<ReplyVO> listCriteria(int bid,replyPageCriteria replyPageCriteria,BoardType boardType) throws Exception; // 댓글 조회
	public void deleteReply(String rid, BoardType boardType); // 댓글 삭제
	
}
