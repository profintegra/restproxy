package com.web.model;

public class Response {
	
	private String token;
	private int responseCode;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	
	@Override
	public String toString() {
		return String.format("Token=%s \n Status code=%d", this.token, this.responseCode);
	}

}
