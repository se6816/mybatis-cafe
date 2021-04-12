package com.test.domain;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;


public class BbsVO {
	private int bid;
	
	@NotBlank(message="제목을 입력하세요")
	@Size(min=0,max=20,message="제목이 너무 깁니다")
	private String subject;
	@Size(min=1,message="내용을 입력해주세요")
	private String content;
	private String writer;
	private Timestamp regdate;
	private int hit;
	private int bcode;
	private int lovers;
	private int replyCount;
	public BbsVO() {}
	public BbsVO(String writer, String content,String subject,int bcode) {
		this.content=content;
		this.subject=subject;
		this.bcode=bcode;
		this.writer=writer;
	}
	
	
	public BbsVO(int bid,String content,String subject,int bcode) {
		this.content=content;
		this.subject=subject;
		this.bcode=bcode;
		this.bid= bid;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public int getBcode() {
		return bcode;
	}
	public void setBcode(int bcode) {
		this.bcode = bcode;
	}
	public int getLovers() {
		return lovers;
	}
	public void setLovers(int lovers) {
		this.lovers = lovers;
	}
	public int getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	
	
	
	
	
	
	
	
	
	
}
