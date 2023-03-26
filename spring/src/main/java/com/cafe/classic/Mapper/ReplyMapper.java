package com.cafe.classic.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cafe.classic.domain.BbsVO;
import com.cafe.classic.domain.BoardType;
import com.cafe.classic.domain.PageCriteria;
import com.cafe.classic.domain.ReplyVO;
import com.cafe.classic.domain.replyPageCriteria;

@Mapper
public interface ReplyMapper {
	public ReplyVO read(@Param("rid") int rid,@Param("boardType")BoardType boardType);
	public void write(@Param("reply")ReplyVO replyVO,@Param("boardType")BoardType boardType); 
	public List<ReplyVO> listCriteria(@Param("bid") int bid,@Param("replyPageCriteria")replyPageCriteria replyPageCriteria,@Param("boardType") BoardType boardType);
	public void delete(@Param("rid") int rid,@Param("boardType")BoardType boardType);
	public void reWrite(@Param("reply")ReplyVO replyVO,@Param("boardType")BoardType boardType);
	public void addRstep(@Param("reply")ReplyVO replyVO,@Param("boardType")BoardType boardType);
	
}
