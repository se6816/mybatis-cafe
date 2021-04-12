package com.test.Utils;

import java.util.ArrayList;
import java.util.HashMap;
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

	private final static int NOT_EXIST_FILE=0;   // 삭제해야할 파일
	private final static int SAVE_EXIST_FILE=1;  // 저장해야할 파일
	
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
		
		HashMap<Integer,Integer> hash= new HashMap<Integer,Integer>();
		List<Integer> list=FileMapper.findContainedFile(bvo.getBid(), boardType);
		
		if(!list.isEmpty()) {
			for(int item : list) {
				hash.put(item,NOT_EXIST_FILE);
			}
		}
		if(wrd.existFile()) {
			for(int item : wrd.getFid()) {
				if(hash.containsKey(item)) {
					hash.remove(item);
				}
				else {
					hash.put(item,SAVE_EXIST_FILE);
				}
			}
		}
		ArrayList<Integer> del_List=new ArrayList();
		ArrayList<Integer> save_List=new ArrayList();
		for(Entry<Integer,Integer> entry : hash.entrySet()) {
			if(entry.getValue()==SAVE_EXIST_FILE) {
				save_List.add(entry.getKey());
			}
			else {
				del_List.add(entry.getKey());
			}
		}
		int[] saveId=save_List.stream().mapToInt(Integer::intValue).toArray();
		int[] delId=del_List.stream().mapToInt(Integer::intValue).toArray();
		if(saveId.length>0) FileMapper.registerBidAll(saveId, bvo.getBid(), boardType);
		if(delId.length>0) FileMapper.deleteFileAll(delId, boardType);
		BbsMapper.update(bvo, boardType);
	}
}
