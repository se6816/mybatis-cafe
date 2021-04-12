package com.test.domain;

public class PageCriteria {
	private int page;
	protected int numPerPage;
	private SortType sortType;
	private int bcode;
	
	private String findType;
	private String keyword;

	public PageCriteria() {
		this.page = 1;
		this.numPerPage = 20;
		this.sortType=SortType.recent;
		this.bcode=0;
	}
	public void setPage(int page) {
		if(page<=0) {
			this.page = 1;
			return;
		}
		this.page=page;
	}
	public int getPage() {
		return page;
	}
	public String getDB_Sort() {
		return this.sortType.name();
	}
	public int getStartPage() {
		return (this.page-1)*numPerPage;
	}
	public int getNumPerPage() {
		return this.numPerPage;
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
