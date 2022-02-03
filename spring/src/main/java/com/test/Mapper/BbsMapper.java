package com.test.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.test.domain.BbsListVO;
import com.test.domain.BbsVO;
import com.test.domain.BoardType;
import com.test.domain.Page;
import com.test.domain.PageCriteria;
import com.test.domain.UserVO;
import com.test.domain.userBbsVO;
import com.test.domain.userReplyVO;


public interface BbsMapper {
		
		public void insert(@Param("bvo") BbsVO bvo, @Param("boardType")BoardType boardType) throws Exception;
		public BbsVO read(@Param("bid")int bid,@Param("boardType") BoardType boardType) throws Exception;
		public void update(@Param("bvo")BbsVO bvo,@Param("boardType") BoardType boardType) throws RuntimeException;
		public void delete(@Param("bid")int bid, @Param("boardType")BoardType boardType) throws Exception;
		public List<BbsListVO> listCriteria(@Param("pageCria") PageCriteria pageCria,@Param("boardType") BoardType boardType) throws Exception;
		public int countData(@Param("pageCria") Page pageCria, @Param("boardType") BoardType boardType) throws Exception;
		public void Bhit(@Param("bid")int bid,@Param("boardType") BoardType boardType) throws Exception;
		
		public List<BbsVO> hotArticle(@Param("pageCria") PageCriteria pageCria,@Param("boardType") BoardType boardType) throws Exception;
		public List<BbsVO> todayArticle(@Param("pageCria") PageCriteria pageCria,@Param("boardType") BoardType boardType) throws Exception;
		
		public int isClickLovers(@Param("bid")int bid,@Param("userid")String userid,@Param("boardType") BoardType boardType);
		public void addClickLovers(@Param("bid")int bid,@Param("userid")String userid,@Param("boardType") BoardType boardType);

		public int userCountData(String id) throws Exception;
		public int userReplyCountData(String id) throws Exception;
		public List<userBbsVO> userListCriteria(@Param("pageCria") Page page,@Param("id") String id) throws Exception;
		public List<userReplyVO> userReplyListCriteria(@Param("pageCria") Page page,@Param("id") String id) throws Exception;
}
