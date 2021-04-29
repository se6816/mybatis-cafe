package com.test.domain;

import java.sql.Timestamp;

public class Log {
	private final String login_userid;
	private final String login_ip;
	private final boolean login_success;
	private Timestamp login_date;

	public Log(String login_userid, String login_ip, boolean login_success) {

		this.login_userid = login_userid;
		this.login_success = login_success;
		this.login_ip = login_ip;
	}
	public String getLogin_userid() {
		return login_userid;
	}
	public boolean isLogin_success() {
		return login_success;
	}
	public String getLogin_ip() {
		return login_ip;
	}
	public Timestamp getLogin_date() {
		return login_date;
	}
	
	
}
