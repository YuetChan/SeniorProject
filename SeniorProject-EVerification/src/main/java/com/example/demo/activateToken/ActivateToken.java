package com.example.demo.activateToken;

import java.time.LocalDateTime;

public class ActivateToken {
	
	private int userId;
	private String tokenString;
	private LocalDateTime expirationDate;
	
	public ActivateToken(int userId, String tokenString, LocalDateTime expirationDate) {
		
		super();
		
		this.userId = userId;
		
		this.tokenString = tokenString;
		this.expirationDate = expirationDate;
		
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
