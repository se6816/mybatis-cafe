package com.test.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.Mapper.ReplyMapper;
import com.test.domain.BoardType;
import com.test.domain.ReplyVO;
import com.test.domain.replyPageCriteria;

/**
 * 댓글 Service 구현체
 * 2021.12.20
 * @author user
 *
 */
@Service
public class ReplyServiceImpl implements ReplyService {
	/**
	 * 댓글 작성 관련 mapper
	 */
	private final ReplyMapper replyMapper;
	
	@Autowired
	public ReplyServiceImpl(ReplyMapper replyMapper) {
		this.replyMapper = replyMapper;
	}


	/**
	 * 댓글을 작성한다.
	 * @param reply 댓글 양식
	 * @param boardType 게시판 종류
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void write(ReplyVO reply, BoardType boardType) { 
		
		// 이 요청이 댓글에 대한 답글이 아니라면
		if(reply.isReply()) replyMapper.write(reply, boardType);
		else {
			
			// 댓글에 대한 답글 작성
			replyMapper.addRstep(reply, boardType);
			replyMapper.reWrite(reply, boardType);
		}
	}

	/**
	 * 댓글 목록을 출력한다.
	 * @param bid 글 번호
	 * @param replyPageCriteria 댓글 페이지 정보
	 * @param boardType 게시판 종류
	 * @return 댓글 목록
	 */
	@Override
	public List<ReplyVO> listCriteria(int bid,replyPageCriteria replyPageCriteria, BoardType boardType) throws Exception {
		return replyMapper.listCriteria(bid, replyPageCriteria, boardType);
	}


	/**
	 * 댓글을 삭제한다.
	 * @param rid 댓글 번호
	 * @param boardType 게시판 종류
	 */
	@Override
	public void deleteReply(String rid, BoardType boardType) {
		replyMapper.delete(Integer.parseInt(rid), boardType);
		
	}
	
	



}
