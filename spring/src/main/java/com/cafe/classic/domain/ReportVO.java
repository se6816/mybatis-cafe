package com.cafe.classic.domain;

import javax.validation.constraints.NotBlank;

public class ReportVO {
	private String id;
	@NotBlank(message="글이 존재하지 않습니다")
	private String bid;
	private String content;
	private reportType reportType;
	private BoardType boardType;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public reportType getReportType() {
		return reportType;
	}
	public void setReportType(reportType reportType) {
		this.reportType = reportType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "reportVO [id=" + id + ", bid=" + bid + ", content=" + content + ", reportType=" + reportType + "]";
	}
	public BoardType getBoardType() {
		return boardType;
	}
	public void setBoardType(BoardType boardType) {
		this.boardType = boardType;
	}
	
	
	
}
