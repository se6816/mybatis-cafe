package com.test.Service;

import org.springframework.stereotype.Service;

import com.test.domain.Log;
import com.test.domain.UserVO;

public interface UserService {
	public UserVO selectMember(String id);
	public void insertMember(UserVO userVO); 
	public int isID(String id);
	public int isUserName(String username);
	public void deleteMember(String uid);
	public void writeLog(Log log);
	
}
