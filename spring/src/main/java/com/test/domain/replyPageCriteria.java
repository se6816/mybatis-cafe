package com.test.domain;

public class replyPageCriteria extends PageCriteria{

	private final int replyNumPerPage=10;
	
	
	@Override
	public int getNumPerPage() {
		return replyNumPerPage;
	}
}
