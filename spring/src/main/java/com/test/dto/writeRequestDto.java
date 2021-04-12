package com.test.dto;

import java.util.Arrays;
import java.util.HashSet;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.test.domain.BbsVO;

public class writeRequestDto {
	@NotBlank(message="제목을 입력하세요")
	@Size(min=0,max=20,message="제목이 너무 깁니다")
	private String subject;
	@Size(min=1,message="내용을 입력해주세요")
	private String content;
	private int bcode;
	private int[] fid;
	private int bid;
	private String writer;
	
	public BbsVO makeBbsVO() {
		return new BbsVO(writer,content, subject,bcode);
	}
	public BbsVO makeModifyBbsVO() {
		return new BbsVO(bid, content,subject,bcode);
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
	public int[] getFid() {
		return fid;
	}
	public void setFid(int[] fid) {
		if(fid!=null) {
			this.fid=Arrays.copyOf(fid, fid.length);
			return;
		}
		this.fid=null;
	}
	public boolean existFile() {
		if(fid.length>0) {
			return true;
		}
		return false;
	}
	public int getBcode() {
		return bcode;
	}
	public void setBcode(int bcode) {
		this.bcode = bcode;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	
	
	
}
