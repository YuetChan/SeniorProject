package com.example.demo.domain.entity;

import java.time.LocalDateTime;

public class LoginToken {
	
	private int userId;
	private String tokenString; 
	private LocalDateTime expirationDate;
	
	public LoginToken() {
		
		super();
		
	}
	
	public boolean isExpired() {
		
		return LocalDateTime.now().isAfter(expirationDate);
		
	}
	
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
	
	public LocalDateTime getExpirationDate() {
		
		return expirationDate;
		
	}
	
	public void setExpirationDate(LocalDateTime expirationDate) {
		
		this.expirationDate = expirationDate;
		
	}
	
}
