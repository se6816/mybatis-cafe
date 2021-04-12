package com.test.config.auto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.test.domain.UserVO;

public class PrincipalDetails implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserVO UserVO;
	
	
	public PrincipalDetails(UserVO userVO) {
		this.UserVO = userVO;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		collectors.add(new GrantedAuthority() {

			@Override
			public String getAuthority() {
				
				return UserVO.getRoleName();
			}
			
		});
		return collectors;
	}

	@Override
	public String getPassword() {
		
		return UserVO.getPassword();
	}
	public String getEmail() {
		return UserVO.getEmail();
	}
	public Timestamp getRegdate() {
		return UserVO.getRegdate();
	}
	public Timestamp getHaltdate() {
		return UserVO.getHaltDate();
	}
	
	public String getId() {
		return UserVO.getId();
	}
	@Override
	public String getUsername() {
		return UserVO.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
