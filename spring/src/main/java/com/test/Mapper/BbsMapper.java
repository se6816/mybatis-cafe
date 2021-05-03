package com.test.Mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import com.test.domain.BbsVO;
import com.test.domain.BoardType;
import com.test.domain.PageCriteria;


public interface BbsMapper {
		
		public void insert(@Param("bvo") BbsVO bvo, @Param("boardType")BoardType boardType) throws Exception;
		public BbsVO read(@Param("bid")int bid,@Param("boardType") BoardType boardType) throws Exception;
		public void update(@Param("bvo")BbsVO bvo,@Param("boardType") BoardType boardType) throws Exception;
		public void delete(@Param("bid")int bid, @Param("boardType")BoardType boardType) throws Exception;
		public List<BbsVO> listCriteria(@Param("pageCria") PageCriteria pageCria,@Param("boardType") BoardType boardType) throws Exception;
		public int countData(@Param("pageCria") PageCriteria pageCria, @Param("boardType") BoardType boardType) throws Exception;
		public void Bhit(@Param("bid")int bid,@Param("boardType") BoardType boardType) throws Exception;
		
		public List<BbsVO> hotArticle(@Param("pageCria") PageCriteria pageCria,@Param("boardType") BoardType boardType) throws Exception;
		public List<BbsVO> todayArticle(@Param("pageCria") PageCriteria pageCria,@Param("boardType") BoardType boardType) throws Exception;
		
		public int isClickLovers(@Param("bid")int bid,@Param("userid")String userid,@Param("boardType") BoardType boardType);
		public void addClickLovers(@Param("bid")int bid,@Param("userid")String userid,@Param("boardType") BoardType boardType);
		public void deleteClickLovers(@Param("bid")int bid,@Param("userid")String userid,@Param("boardType") BoardType boardType);

		
		
}
