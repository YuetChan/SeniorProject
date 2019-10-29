package com.example.demo.request;

import java.util.ArrayList;

public class SecondLoginRequest {
	
	private String tokenString;
	private ArrayList<String> imageDataUrls;
	
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
