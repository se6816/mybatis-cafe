package com.cafe.classic.config.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cafe.classic.Service.user.UserService;
import com.cafe.classic.domain.UserVO;

@Service
public class PrincipalDetailsService implements UserDetailsService {
	
	private final UserService uSvc;
	public PrincipalDetailsService(UserService uSvc) {
		this.uSvc = uSvc;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserVO user= uSvc.selectMember(username);
		if(user==null) {
			throw new UsernameNotFoundException("사용자가 입력한 아이디는 존재하지않습니다.");
		}
		
		return new PrincipalDetails(user);
	}

}
