package com.test.config.auto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.test.Service.UserService;
import com.test.domain.UserVO;

@Service
public class PrincipalDetailsService implements UserDetailsService {
	
	@Autowired
	private UserService uSvc;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserVO user= uSvc.selectMember(username);
		if(user==null) {
			throw new UsernameNotFoundException("사용자가 입력한 아이디는 존재하지않습니다.");
		}
		
		return new PrincipalDetails(user);
	}

}
