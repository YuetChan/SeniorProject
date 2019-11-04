package com.example.demo.app.request;

import java.util.ArrayList;

public class SecondLoginRequest {
	
	private int userId;
	private String tokenString;
	private ArrayList<String> imageDataUrls;
	
	public int getUserId() {
		
		return userId;
		
	}

	public void setUserId(int userId) {
		
		this.userId = userId;
		
	}

	public String getTokenString() {
		
		return tokenString;
		
	}
	
	public void setTokenString(String tokenString) {
		
		this.tokenString = tokenString;
		
	}

	public ArrayList<String> getImageDataUrls() {
		
		return imageDataUrls;
		
	}

	public void setImageDataUrls(ArrayList<String> imageDataUrls) {
		
		this.imageDataUrls = imageDataUrls;
		
	}
	
}
