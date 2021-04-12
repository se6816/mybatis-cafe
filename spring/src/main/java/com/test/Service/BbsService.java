package com.test.Service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.test.domain.BbsVO;
import com.test.domain.BoardType;
import com.test.domain.PageCriteria;
import com.test.dto.writeRequestDto;

public interface BbsService {
	public void write(writeRequestDto wrd,BoardType boardType) throws Exception;
	public BbsVO read(int bid,BoardType boardType) throws Exception;
	public void modify(writeRequestDto wrd,BoardType boardType) throws Exception;
	public void remove(int bid, BoardType boardType) throws Exception;
	public List<BbsVO> listCriteria(PageCriteria PageCria, BoardType boardType) throws Exception;
	public int countData(PageCriteria pageCria, BoardType boardType) throws Exception;
	public List<BbsVO> hotShowArticle(PageCriteria pageCria, BoardType boardType) throws Exception;
	public List<BbsVO> todayShowArticle(PageCriteria pageCria, BoardType boardType) throws Exception;
	public int clicklovers(int bid,String id,BoardType boardType);
	public boolean isClickLovers(int bid,String id,BoardType boardType);
}
