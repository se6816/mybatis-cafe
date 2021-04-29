package com.test.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class EmailRequest {
	
	@NotBlank(message="이메일을 입력해주세요")
	@Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message="이메일 형식이 아닙니다.")
	private String email;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
