package com.test.Mapper;

import org.apache.ibatis.annotations.Mapper;

import com.test.domain.Log;
import com.test.domain.UserVO;
import com.test.domain.reportVO;


@Mapper
public interface UserMapper {
	public int isUserName(String username);  // 닉네임 중복 여부
	public int isID(String id); // ID 중복 여부
	public int isEmail(String email);
	public void loginMember(UserVO UserVO); // 록그인 
	public UserVO selectMemberFromEmail(String email); // 회원을 이메일로 조회
	public UserVO selectMember(String id); // 회원을 id로 조회
	public void insertMember(UserVO UserVO); // 회원 가입
	public void deleteMember(String id); // 탈퇴
	public UserVO readMember(int uid); // 회원수정할때
	public void reportAdmin(reportVO report); // 신고하기
	public void UpdatePassword(UserVO userVO); // 패스워드 바꾸기
	public void log(Log log); // 로그 기록

}
