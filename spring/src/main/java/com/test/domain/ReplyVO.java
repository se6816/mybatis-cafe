package com.test.domain;

import java.sql.Timestamp;

import javax.validation.constraints.Size;

public class ReplyVO {
	
	private String rid;
	private int bid;
	@Size(min=1,max=1000,message="글자수가 1000자 이하여야합니다")
	private String content;
	private String writer;
	private Timestamp regdate;
	private int rgroup;
	private int rstep;
	private String Bwriter;
	private boolean secret;
	private boolean delYn;
	private int length;
	
	
	public ReplyVO() {
		this.rgroup=0;
		this.rstep=-1;
	}
	
	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public void setLength(int length) {
		this.length = length;
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
	public String getBwriter() {
		return Bwriter;
	}
	public void setBwriter(String bwriter) {
		Bwriter = bwriter;
	}
	
	
	public boolean isReply(){
		if(this.rgroup==0) {
			return true;
		}
		return false;
	}
	
	
	
	
	
}
