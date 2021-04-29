package com.test.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.Mapper.ReplyMapper;
import com.test.domain.BoardType;
import com.test.domain.ReplyVO;
import com.test.domain.replyPageCriteria;

@Service
public class ReplyServiceImpl implements ReplyService {

	private final ReplyMapper replyMapper;
	
	@Autowired
	public ReplyServiceImpl(ReplyMapper replyMapper) {
		this.replyMapper = replyMapper;
	}


	@Override
	@Transactional(rollbackFor=Exception.class)
	public void write(ReplyVO reply, BoardType boardType) {
		if(reply.getRgroup()==0) replyMapper.write(reply, boardType);
		else {
			replyMapper.addRstep(reply, boardType);
			replyMapper.reWrite(reply, boardType);
		}
	}

	@Override
	public List<ReplyVO> listCriteria(int bid,replyPageCriteria replyPageCriteria, BoardType boardType) throws Exception {
		return replyMapper.listCriteria(bid, replyPageCriteria, boardType);
	}


	@Override
	public void deleteReply(int rid, BoardType boardType) {
		replyMapper.delete(rid, boardType);
		
	}



}
