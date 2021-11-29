package com.test.domain;

public class PageCriteria extends Page{
	private SortType sortType;
	private int bcode;
	private String findType;
	private String keyword;

	public PageCriteria() {
		this.sortType=SortType.recent;
		this.bcode=0;
	}
	public String getDB_Sort() {
		return this.sortType.name();
	}
	public SortType getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = SortType.valueOf(sortType.toLowerCase());
	}
	public int getBcode() {
		return bcode;
	}
	public void setBcode(int bcode) {
		this.bcode = bcode;
	}
	public String getFindType() {
		return findType;
	}
	public void setFindType(String findType) {
		this.findType = findType;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}		
	
	
	
	
	
}
