package com.test.domain;

import java.sql.Timestamp;

import javax.validation.constraints.Size;

public class ReplyVO {
	
	private int rid;
	private int bid;
	@Size(min=1,max=1000,message="글자수가 1000자 이하여야합니다")
	private String content;
	private String writer;
	private Timestamp regdate;
	private int rgroup;
	private int rstep;
	private boolean secret;
	private boolean delYn;
	private int length;
	
	
	public ReplyVO() {
		this.rgroup=0;
		this.rstep=-1;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
		this.length=content.length();
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Timestamp getRegdate() {
		return regdate;
	}
	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}
	public int getRgroup() {
		return rgroup;
	}
	public void setRgroup(int rgroup) {
		this.rgroup = rgroup;
	}
	public int getRstep() {
		return rstep;
	}
	public void setRstep(int rstep) {
		this.rstep = rstep;
	}
	public boolean isSecret() {
		return secret;
	}
	public void setSecret(boolean secret) {
		this.secret = secret;
	}
	public boolean isDelYn() {
		return delYn;
	}
	public void setDelYn(boolean delYn) {
		this.delYn = delYn;
	}
	public int getLength() {
		return length;
	}
	
	
	
	
	
	
	
}
