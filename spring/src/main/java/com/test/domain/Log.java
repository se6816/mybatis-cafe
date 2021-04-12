package com.test.domain;

public class Log {
	private String username;
	private String ip;
	public Log(String username, String ip) {
		this.username=username;
		this.ip=ip;
	}
	public String getUsername() {
		return username;
	}
	public String getIp() {
		return ip;
	}
	
}
