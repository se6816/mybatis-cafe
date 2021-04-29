package com.test.domain;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PagingMaker {
	private int totalData; // 전체 데이터 갯수
	private int startPage; // 페이지목록의 시작번호
	private int endPage; //  페이지목록의  끝번호
	private boolean prev; // 이전 버튼 유무
	private boolean next; // 다음 버튼 유무
	
	private static final int displayPageNum=10;  // 페이지목록에 나타낼 페이지 번호의 갯수
	
	private PageCriteria pageCria;
	
	public void constructData(PageCriteria pageCria, int totalData) {
		setCriteria(pageCria);
		setTotalData(totalData);
		getPagingData();
	}
	private void setCriteria(PageCriteria pageCria) {
		this.pageCria=pageCria;
	}
	
	private void setTotalData(int totalData) {
		this.totalData = totalData;
	}
	
	
	
	private void getPagingData() {
		endPage=(int)Math.ceil(pageCria.getPage()/(double)displayPageNum)*displayPageNum;
		startPage=(endPage - displayPageNum)+1;
		
		int finalEndPage=(int)Math.ceil(totalData/(double)pageCria.getNumPerPage());
		
		if(endPage > finalEndPage) {
			endPage= finalEndPage;
		}
		prev= startPage ==1 ? false:true;
		
		next = (endPage*pageCria.getNumPerPage())>=totalData? false:true;
		
		
	}
	
	public String makeURI(int page,int bcode) {
		UriComponents uriComponents=UriComponentsBuilder.newInstance()
				.queryParam("page",page)
//				.queryParam("numPerPage", pageCria.getNumPerPage())
				.queryParam("bcode",bcode)
				.queryParam("findType",pageCria.getFindType())
				.queryParam("keyword", pageCria.getKeyword())
				.queryParam("sortType",pageCria.getSortType().name())
				
				.build();
		return uriComponents.toString();
	}
	public String remakeURI(int page,int bcode) {
		UriComponents uriComponents=UriComponentsBuilder.newInstance()
				.queryParam("page",page)
//				.queryParam("numPerPage", pageCria.getNumPerPage())
				.queryParam("bcode",bcode)
				.queryParam("findType",pageCria.getFindType())
				.queryParam("keyword", pageCria.getKeyword())
				.build();
		return uriComponents.toString();
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	public PageCriteria getPageCria() {
		return pageCria;
	}
	public void setPageCria(PageCriteria pageCria) {
		this.pageCria = pageCria;
	}
	public int getTotalData() {
		return totalData;
	}
	public static int getDisplaypagenum() {
		return displayPageNum;
	}
	
	
	
	
}
