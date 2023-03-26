package com.cafe.classic.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserVO implements Serializable{
	public interface ValidateEmail{}
	
	@NotBlank(message="아이디를 입력하세요")
	@Size(max=20, message="아이디가 너무 깁니다(최대 20글자)")
	private String id;
	
	@NotBlank(message="비밀번호를 입력하세요")
	@Size(min=8, max=15, message="비밀번호 길이가 맞지 않습니다(최소 8글자 최대 15글자)")
	private String password;
	
	@NotBlank(message="활동명을 입력하세요")
	@Size(min=3, max=10, message="아이디 길이가 맞지 않습니다(최소 3글자 최대 10글자)")
	private String username;
	
	@NotBlank(message="이메일을 입력해주세요", groups=ValidateEmail.class)
	@Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message="이메일 형식이 아닙니다.",groups=ValidateEmail.class)
	private String email;
	private RoleType role;
	private Timestamp regdate;
	private Timestamp haltDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Timestamp getRegdate() {
		return regdate;
	}
	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}
	public Timestamp getHaltDate() {
		return haltDate;
	}
	public void setHaltDate(Timestamp haltDate) {
		this.haltDate = haltDate;
	}
	public RoleType getRole() {
		return role;
	}
	public void setRole(RoleType role) {
		this.role = role;
	}
	public String getRoleName() {
		return this.getRole().name();
	}
	@Override
	public String toString() {
		return "id:"+this.id+",pw:"+this.password;
	}
	
	
	

}
