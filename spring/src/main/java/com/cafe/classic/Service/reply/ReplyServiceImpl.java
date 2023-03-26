package com.cafe.classic.Service.reply;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafe.classic.Mapper.ReplyMapper;
import com.cafe.classic.config.auth.PrincipalDetails;
import com.cafe.classic.domain.BoardType;
import com.cafe.classic.domain.ReplyVO;
import com.cafe.classic.domain.replyPageCriteria;

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
	
	private final String SECURE_REPLY="비밀글입니다";
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
	public List<ReplyVO> listCriteria(int bid,replyPageCriteria replyPageCriteria, BoardType boardType, String username) {
		List<ReplyVO> list=replyMapper.listCriteria(bid, replyPageCriteria, boardType);
		setSecretArticle(list,username);
		return list;
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

	/**
	 * 특정 댓글 읽기
	 * @param rid 댓글 번호
	 * @param boardType 게시판 종류
	 */
	@Override
	public ReplyVO read(String rid, BoardType boardType) {
		 return replyMapper.read(Integer.parseInt(rid), boardType);
	}

	
	
	/**
	 * 비밀글 활성화 
	 * @param list 댓글 리스트
	 * @param username 접속 당사자
	 */
	private void setSecretArticle(List<ReplyVO> list, String username) {
		for(ReplyVO reply : list) {
			if(!reply.isSecret()) {
				continue;
			}
			if(!reply.isAuthentication(username)) {
				reply.setContent(SECURE_REPLY);
			}
		}
	}
}
