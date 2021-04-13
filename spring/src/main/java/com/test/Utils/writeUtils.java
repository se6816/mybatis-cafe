package com.test.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.Mapper.BbsMapper;
import com.test.Mapper.FileMapper;
import com.test.Service.BbsService;
import com.test.Service.UploadService;
import com.test.domain.BbsVO;
import com.test.domain.BoardType;
import com.test.dto.writeRequestDto;

@Service
public class writeUtils {
	
	private final BbsMapper BbsMapper;
	private final FileMapper FileMapper;
	
	@Autowired
	public writeUtils(BbsMapper bbsMapper, FileMapper fileMapper) {
		BbsMapper = bbsMapper;
		FileMapper = fileMapper;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void writeAndUpload(writeRequestDto wrd, BoardType boardType) throws Exception {
		BbsVO bvo= wrd.makeBbsVO();
		BbsMapper.insert(bvo, boardType);
		if(wrd.existFile()) {
			FileMapper.registerBidAll(wrd.getFid(), bvo.getBid(), boardType);
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void modifyAndUpload(writeRequestDto wrd, BoardType boardType) throws Exception {
		BbsVO bvo= wrd.makeModifyBbsVO();
		
		HashSet<Integer> hash= new HashSet<>();
		List<Integer> list=FileMapper.findContainedFile(bvo.getBid(), boardType);
		
		if(!list.isEmpty()) {
			for(int item : list) {
				hash.add(item);
			}
		}
		if(wrd.existFile()) {
			for(int item : wrd.getFid()) {
				if(hash.contains(item)) {
					hash.remove(item);
				}
			}
		}
		ArrayList<Integer> del_List=new ArrayList(hash);
		int[] delId=del_List.stream().mapToInt(Integer::intValue).toArray();
		if(wrd.existFile())FileMapper.registerBidAll(wrd.getFid(), bvo.getBid(), boardType);
		BbsMapper.update(bvo, boardType);
		if(delId.length>0) FileMapper.deleteFileAll(delId, boardType);
		
	}
}
