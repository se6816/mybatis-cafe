package com.test.domain;

import java.util.ArrayList;
import java.util.Arrays;

public enum UserCategory {
	CHANGEPW("비밀번호 변경"
			,"changepw"),
	CHANGEINFO("회원정보 변경"
			,"userinfo");
	private UserCategory(String boardName, String url) {
				this.boardName = boardName;
				this.url = url;
			}
	private String boardName;
	private String url;

	public int getCategoryLength() {
		return UserCategory.values().length;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

}
