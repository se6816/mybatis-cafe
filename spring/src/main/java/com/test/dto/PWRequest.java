package com.test.dto;

import javax.validation.constraints.Size;

public class PWRequest {
	
	private String key;
	@Size(min=8, max=20,message="새 비밀번호를 입력해주세요(8~20글자)")
	private String new_passwd;
	
	public String getNew_passwd() {
		return new_passwd;
	}
	public void setNew_passwd(String new_passwd) {
		this.new_passwd = new_passwd;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	
	
}
