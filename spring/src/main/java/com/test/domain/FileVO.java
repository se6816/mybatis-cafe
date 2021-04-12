package com.test.domain;

import java.sql.Timestamp;


public class FileVO {
	private int FILEID;
	private String PathName;
	private String FileName;
	private String Original_Filename;
	private int FileSize;
	private String Content_Type;
	private String ReSource_PathName;

	public int getFILEID() {
		return FILEID;
	}
	public void setFILEID(int fILEID) {
		this.FILEID = fILEID;
	}
	public String getPathName() {
		return PathName;
	}
	public void setPathName(String pathName) {
		this.PathName = pathName;
	}
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		this.FileName = fileName;
	}
	public String getOriginal_Filename() {
		return Original_Filename;
	}
	public void setOriginal_Filename(String original_Filename) {
		this.Original_Filename = original_Filename;
	}
	public int getFileSize() {
		return FileSize;
	}
	public void setFileSize(int fileSize) {
		this.FileSize = fileSize;
	}
	public String getContent_Type() {
		return Content_Type;
	}
	public void setContent_Type(String content_Type) {
		this.Content_Type = content_Type;
	}
	public String getReSource_PathName() {
		return ReSource_PathName;
	}
	public void setReSource_PathName(String reSource_PathName) {
		this.ReSource_PathName = reSource_PathName;
	}
	@Override
	public String toString() {
		return "FileVO [FILEID=" + FILEID + ", PathName=" + PathName + ", FileName=" + FileName
				+ ", Original_Filename=" + Original_Filename + ", FileSize=" + FileSize + ", Content_Type=" + Content_Type
				+ ", ReSource_PathName=" + ReSource_PathName + "]";
	}
	
}
