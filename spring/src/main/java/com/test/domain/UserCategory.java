package com.test.domain;

import java.util.ArrayList;
import java.util.Arrays;

public enum UserCategory {
	bbs("작성한 글보기"
			,"/user/info/bbs"),
	reply("작성한 댓글"
			,"/user/info/reply");
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
