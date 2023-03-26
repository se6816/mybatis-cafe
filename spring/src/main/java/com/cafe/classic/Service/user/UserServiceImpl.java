package com.cafe.classic.Service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cafe.classic.Mapper.UserMapper;
import com.cafe.classic.domain.RoleType;
import com.cafe.classic.domain.UserVO;
import com.cafe.classic.domain.ReportVO;
/**
 * 유저 Service interface
 * 2021.2.04
 * @author user
 *
 */
@Service
public class UserServiceImpl implements UserService{
	/**
	 * 계정 관련 mapper
	 */
	private final UserMapper userMapper;
	
	/**
	 * 비번 암호화
	 */
	private final BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
	

	public UserServiceImpl(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
    /**
     * 계정을 추가한다.
     * @param UserVO 계정 정보
     */
	public void insertMember(UserVO UserVO){
		String rawpassword=UserVO.getPassword();
		String encpw=encoder.encode(rawpassword);
		UserVO.setPassword(encpw);
		UserVO.setRole(RoleType.MEMBER);
		userMapper.insertMember(UserVO);
	}

	/**
	 * @param id 아이디
	 * @return 
	 */
	@Override
	public int isID(String id) {
		return userMapper.isID(id);
		
	}

	@Override
	public int isUserName(String username) {
		return userMapper.isUserName(username);
		
	}

	@Override
	public UserVO selectMember(String id) {
		return userMapper.selectMember(id);
	}

	@Override
	public void deleteMember(String uid) {
		userMapper.deleteMember(uid);
	}

	@Override
	public void report(ReportVO report) {
		userMapper.reportAdmin(report);
	}

	@Override
	public void updatePW(UserVO userVO) {
		String rawpassword=userVO.getPassword();
		String encpw=encoder.encode(rawpassword);
		userVO.setPassword(encpw);
		userMapper.UpdatePassword(userVO);
		
	}

	@Override
	public UserVO selectMemberFromEmail(String email) {
		return userMapper.selectMemberFromEmail(email);
	}

	@Override
	public int isEmail(String email) {
		return userMapper.isEmail(email);
	}

	
	
}
