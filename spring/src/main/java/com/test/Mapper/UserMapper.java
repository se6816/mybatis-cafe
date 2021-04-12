package com.test.Mapper;

import org.apache.ibatis.annotations.Mapper;

import com.test.domain.Log;
import com.test.domain.UserVO;
import com.test.domain.reportVO;

@Mapper
public interface UserMapper {
	public int isUserName(String username);
	public int isID(String id);
	public void loginMember(UserVO UserVO); // 록그인 
	public UserVO selectMember(String id);
	public void insertMember(UserVO UserVO);
	public void deleteMember(String id); // 탈퇴
	public UserVO readMember(int uid); // 회원수정할때
	public void report(reportVO report);
	public void UpdateMember(UserVO memberVO); // 회원수정 시도
	public void UpdatePassword(String Password); // 패스워드 바꾸기
	

}
