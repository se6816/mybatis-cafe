package com.cafe.classic.domain;


public enum MESSAGE_CODE {
	PageNotFoundException("페이지가 존재하지 않습니다"),
	NoAuthException("권한이 없습니다"),
	WRITE_ARTICLE_SUCCESS("게시물 작성이 완료되었습니다."),
	UPDATE_ARTICLE_SUCCESS("게시물이 성공적으로 수정되었습니다."),
	DELETE_ARTICLE_SUCCESS("게시물이 성공적으로 삭제되었습니다."),
	UPLOAD_FILE_FAIL("파일 업로드에 실패했습니다"),
	FILE_DOWNLOAD_FAIL("파일 다운로드에 실패하였습니다."),
	JOIN_USER_SUCCESS("성공적으로 가입되었습니다"),
	ALREADY_EXIST_USERNAME("활동명이 이미존재합니다."),
	ALREADY_EXIST_ID("아이디가 이미존재합니다."),
	REPLY_SUCCESS("댓글이 입력되었습니다"),
	REPLY_FAIL("댓글을 입력해주세요"),
	REPLY_DELETE_SUCCESS("댓글을 삭제했습니다"),
	USER_WITHDRAW_SUCCESS("계정이 성공적으로 삭제되었습니다."),
	CHANGE_PASSWORD_SUCCESS("비밀번호를 변경하였습니다."),
	REPORT_SUCCESS("신고가 완료되었습니다"),
	DENIED_ACCESS("잘못된 접근입니다"),
	EMAIL_SEND_SUCCESS("성공적으로 이메일이 발송되었습니다"),
	EMAIL_CHECK_FAIL("이메일 확인이 되지 않았습니다."),
	EMAIL_ALREADY_EXISTS("존재하는 이메일입니다"),
	KEY_ALREADY_EXPIRED("키가 만료되었습니다")
	;
	
	private String message;

	private MESSAGE_CODE(String message) {
		this.message=message;
		// TODO Auto-generated constructor stub
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
