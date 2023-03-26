package com.cafe.classic.Mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cafe.classic.domain.BbsListVO;
import com.cafe.classic.domain.BbsVO;
import com.cafe.classic.domain.BoardType;
import com.cafe.classic.domain.Category;
import com.cafe.classic.domain.Page;
import com.cafe.classic.domain.PageCriteria;
import com.cafe.classic.domain.UserVO;
import com.cafe.classic.domain.UserBbsVO;
import com.cafe.classic.domain.UserReplyVO;

@Mapper
public interface BbsMapper {
		
		public void insert(@Param("bvo") BbsVO bvo, @Param("boardType")Category boardType);
		public BbsVO read(@Param("bid")int bid,@Param("boardType") Category boardType);
		public void update(@Param("bvo")BbsVO bvo,@Param("boardType") Category boardType) throws RuntimeException;
		public void delete(@Param("bid")int bid, @Param("boardType")Category boardType);
		public List<BbsListVO> listCriteria(@Param("pageCria") PageCriteria pageCria,@Param("boardType") Category boardType);
		public int countData(@Param("pageCria") Page pageCria, @Param("boardType") Category boardType);
		
		public List<BbsVO> hotArticle(@Param("pageCria") PageCriteria pageCria,@Param("boardType") BoardType boardType);
		
		public int isClickLovers(@Param("bid")int bid,@Param("userid")String userid,@Param("boardType") BoardType boardType);
		public void addClickLovers(@Param("bid")int bid,@Param("userid")String userid,@Param("boardType") BoardType boardType);

		public int userCountData(String id);
		public int userReplyCountData(String id);
		public List<UserBbsVO> userListCriteria(@Param("pageCria") Page page,@Param("id") String id);
		public List<UserReplyVO> userReplyListCriteria(@Param("pageCria") Page page,@Param("id") String id);
}
