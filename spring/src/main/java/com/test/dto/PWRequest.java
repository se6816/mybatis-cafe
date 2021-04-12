package com.test.dto;

import javax.validation.constraints.Size;

public class PWRequest {
	@Size(min=1, max=20,message="비밀번호를 입력해주세요")
	private String cur_passwd;
	@Size(min=1, max=20,message="새 비밀번호를 입력해주세요")
	private String new_passwd;
	public String getCur_passwd() {
		return cur_passwd;
	}
	public void setCur_passwd(String cur_passwd) {
		this.cur_passwd = cur_passwd;
	}
	public String getNew_passwd() {
		return new_passwd;
	}
	public void setNew_passwd(String new_passwd) {
		this.new_passwd = new_passwd;
	}
	
	
}
