package com.example.demo.request;

public class SecondLoginRequest {
	
	private String tokenString;
	private String imgBase64;
	
	public String getTokenString() {
		
		return tokenString;
		
	}
	
	public void setTokenString(String tokenString) {
		
		this.tokenString = tokenString;
		
	}
	
	public String getImgBase64() {
		
		return imgBase64;
		
	}
	
	public void setImgBase64(String imgBase64) {
		
		this.imgBase64 = imgBase64;
		
	}
	
}
