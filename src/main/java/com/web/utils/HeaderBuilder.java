package com.web.utils;

import java.util.HashMap;
import java.util.Map;

public class HeaderBuilder {
	
	
	private Map<String, String> headers;
	
	public HeaderBuilder(){
		headers = new HashMap<String, String>();
	}
	
	public HeaderBuilder connectionKeepAlive(){
		this.headers.put("Connection", "keep-alive");
		return this;
	}
	
	public HeaderBuilder contentTypeVndXml(){
		this.headers.put("Content-Type", "application/vnd.vizrt.payload+xml");
		return this;
	}
	
	public HeaderBuilder acceptVndXml(){
		this.headers.put("Accept", "application/vnd.vizrt.payload+xml");
		return this;
	}
	
	public HeaderBuilder acceptAtomXml(){
		this.headers.put("Accept", "application/atom+xml");
		return this;
	}
	
	public HeaderBuilder acceptJson(){
		this.headers.put("Accept", "application/json");
		return this;
	}
	
	public HeaderBuilder acceptAll(){
		this.headers.put("Accept", "*/*");
		return this;
	}
	
	public HeaderBuilder authorization(String token){
		this.headers.put("Authorization", token);//"Basic dXVzZXI6SW5tZWRpYQ==");
		return this;
	}
	
	public Map<String, String> build(){
		return this.headers;
	}
	
	
	
		

}
