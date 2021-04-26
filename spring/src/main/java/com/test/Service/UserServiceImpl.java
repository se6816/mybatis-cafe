package com.test.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.test.Mapper.UserMapper;
import com.test.domain.Log;
import com.test.domain.RoleType;
import com.test.domain.UserVO;
import com.test.domain.reportVO;

@Service
public class UserServiceImpl implements UserService{

	
	private final UserMapper userMapper;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	public UserServiceImpl(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public void insertMember(UserVO UserVO){
		String rawpassword=UserVO.getPassword();
		String encpw=encoder.encode(rawpassword);
		UserVO.setPassword(encpw);
		UserVO.setRole(RoleType.ROLE_MEMBER);
		userMapper.insertMember(UserVO);
	}

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
	public void report(reportVO report) {
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
	public void writeLog(Log log) {
		userMapper.log(log);
		
	}

	
	
}
