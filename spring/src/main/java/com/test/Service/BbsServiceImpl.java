package com.test.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.Mapper.BbsMapper;
import com.test.Mapper.FileMapper;
import com.test.Utils.writeUtils;
import com.test.domain.BbsListVO;
import com.test.domain.BbsVO;
import com.test.domain.BoardType;
import com.test.domain.Page;
import com.test.domain.PageCriteria;
import com.test.domain.UserVO;
import com.test.domain.userBbsVO;
import com.test.domain.userReplyVO;
import com.test.dto.writeRequestDto;

@Service
public class BbsServiceImpl implements BbsService {

	private final BbsMapper BbsMapper;
	private final FileMapper FileMapper;
	
	@Autowired
	public BbsServiceImpl(BbsMapper bbsMapper, FileMapper fileMapper) {
		BbsMapper = bbsMapper;
		FileMapper= fileMapper;
	}

	@Override
	public void write(writeRequestDto wrd,BoardType boardType) throws Exception {
		writeUtils writeUtils= new writeUtils(BbsMapper,FileMapper);
		writeUtils.writeAndUpload(wrd, boardType);	
	}

	@Override
	public BbsVO read(int bid,BoardType boardType) throws Exception {
		BbsVO bbs= BbsMapper.read(bid,boardType);
		BbsMapper.Bhit(bid, boardType);
		return bbs;
	}

	@Override
	public void modify(writeRequestDto wrd, BoardType boardType)  throws Exception {
		writeUtils writeUtils= new writeUtils(BbsMapper,FileMapper);
		writeUtils.modifyAndUpload(wrd, boardType);
	}

	@Override
	public void remove(int bid,BoardType boardType) throws Exception {
		BbsMapper.delete(bid,boardType);
	}
	
	@Override
	public List<BbsListVO> listCriteria(PageCriteria pageCria,BoardType boardType) throws Exception {
		return BbsMapper.listCriteria(pageCria,boardType);
		
	}
	@Override
	public int countData(Page pageCria,BoardType boardType) throws Exception {
		return BbsMapper.countData(pageCria, boardType);
	}
	@Override
	public List<BbsVO> hotShowArticle(PageCriteria pageCria, BoardType boardType) throws Exception {
		return BbsMapper.hotArticle(pageCria, boardType);
		
	}
	@Override
	public List<BbsVO> todayShowArticle(PageCriteria pageCria, BoardType boardType) throws Exception {
		return BbsMapper.todayArticle(pageCria, boardType);
		
	}

	

	@Override
	public int clicklovers(int bid, String id, BoardType boardType) {
		int value= BbsMapper.isClickLovers(bid, id, boardType);
		if(value==0) BbsMapper.addClickLovers(bid, id, boardType);
		else if(value>0) BbsMapper.deleteClickLovers(bid, id, boardType);
		return value;
	}

	@Override
	public boolean isClickLovers(int bid, String id, BoardType boardType) {
		int value=BbsMapper.isClickLovers(bid, id, boardType);
		if(value>0) return true;
		
		return false;
	}

	@Override
	public List<userBbsVO> userListCriteria(Page page, String id) throws Exception {
		// TODO Auto-generated method stub
		return BbsMapper.userListCriteria(page, id);
	}

	@Override
	public List<userReplyVO> userReplyListCriteria(Page page, String id) throws Exception {
		// TODO Auto-generated method stub
		return BbsMapper.userReplyListCriteria(page, id);
	}

	@Override
	public int userCountData(String id) throws Exception {
		// TODO Auto-generated method stub
		return BbsMapper.userCountData(id);
	}

	@Override
	public int userReplyCountData(String id) throws Exception {
		return BbsMapper.userReplyCountData(id);
	}

	
	
	
	
	
	
	
}
