package com.test.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.test.domain.BbsVO;
import com.test.domain.BoardType;
import com.test.domain.PageCriteria;
import com.test.domain.ReplyVO;
import com.test.domain.replyPageCriteria;

public interface ReplyMapper {
	public void write(@Param("reply")ReplyVO replyVO,@Param("boardType")BoardType boardType);
	public List<ReplyVO> listCriteria(@Param("bid") int bid,@Param("reply")replyPageCriteria replyPageCriteria,@Param("boardType") BoardType boardType) throws Exception;
	public void delete(@Param("rid") int rid,@Param("boardType")BoardType boardType);
	public void reWrite(@Param("reply")ReplyVO replyVO,@Param("boardType")BoardType boardType);
	public void addRstep(@Param("reply")ReplyVO replyVO,@Param("boardType")BoardType boardType);
	
}
