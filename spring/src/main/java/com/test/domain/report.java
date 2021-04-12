package com.test.domain;

public class report {
	private String id;
	private String bid;
	private String content;
	private reportType reportType;
	
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
	
	
	
}
