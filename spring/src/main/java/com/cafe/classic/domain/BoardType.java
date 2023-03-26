package com.cafe.classic.domain;
import java.util.ArrayList;
import java.util.Arrays;

public enum BoardType implements Category{
	starcraft("스타크래프트","starcraft","starcraft_reply","starcraft_File","starcraft_lovers"
			,new ArrayList<String>(Arrays.asList("전체글보기","자유 게시판","맵 게시판","신고 게시판"))
			,new ArrayList<Integer>(Arrays.asList(0,1,2,3))),
	arcturus("악튜러스","arcturus","arcturus_reply","arcturus_File","arcturus_lovers"
			,new ArrayList<String>(Arrays.asList("전체글보기","자유게시판","질문 게시판","신고 게시판"))
			,new ArrayList<Integer>(Arrays.asList(0,1,2,3)));
	private String boardName;
	private String DB_Table;
	private String DB_Reply_Table;
	private String DB_File_Table;
	private String DB_Lovers_Table;
	private ArrayList<String> Category;
	private ArrayList<Integer> bcode;

	


	private BoardType(String boardName, String dB_Table, String dB_Reply_Table, String dB_File_Table,
			String dB_Lovers_Table, ArrayList<String> category, ArrayList<Integer> bcode) {
		this.boardName = boardName;
		DB_Table = dB_Table;
		DB_Reply_Table = dB_Reply_Table;
		DB_File_Table = dB_File_Table;
		DB_Lovers_Table = dB_Lovers_Table;
		Category = category;
		this.bcode = bcode;
	}

	public int getCategoryLength() {
		return Category.size();
	}

	public ArrayList<Integer> getBcode() {
		return bcode;
	}

	public ArrayList<String> getCategory() {
		return Category;
	}

	public String getDB_Table() {
		return DB_Table;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	public String getDB_Reply_Table() {
		return DB_Reply_Table;
	}
	public String getDB_File_Table() {
		return DB_File_Table;
	}

	public String getDB_Lovers_Table() {
		return DB_Lovers_Table;
	}
	
	
	@Override
	public String getCategoryName() {
		return this.name();
	}

	
	
	
}
