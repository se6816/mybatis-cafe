package com.cafe.classic.Service.user;

import org.springframework.stereotype.Service;

import com.cafe.classic.domain.UserVO;
import com.cafe.classic.domain.ReportVO;
/**
 * 유저 Service interface
 * 2022.2.04
 * @author user
 *
 */
public interface UserService {
	public UserVO selectMember(String id);  // 회원을 id로 조회
	public UserVO selectMemberFromEmail(String email); // 회원 이메일로 조회
	public void insertMember(UserVO userVO);   // 회원 가입
	public int isID(String id); // 아이디 중복 여부
	public int isUserName(String username);   // 닉네임 중복 여부
	public int isEmail(String email); // 이메일 중복 여부
	public void deleteMember(String uid);   // 회원 탈퇴
	public void report(ReportVO report);   // 신고하기
	public void updatePW(UserVO userVO);   // 비밀번호 변경
}
