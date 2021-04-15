package com.test.Service;

import org.springframework.stereotype.Service;

import com.test.domain.Log;
import com.test.domain.UserVO;
import com.test.domain.reportVO;

public interface UserService {
	public UserVO selectMember(String id);  // 회원 조회
	public void insertMember(UserVO userVO);   // 회원 가입
	public int isID(String id); // 아이디 중복 여부
	public int isUserName(String username);   // 닉네임 중복 여부
	public void deleteMember(String uid);   // 회원 탈퇴
	public void report(reportVO report);   // 신고하기
	public void updatePW(UserVO userVO);   // 비밀번호 변경
}
