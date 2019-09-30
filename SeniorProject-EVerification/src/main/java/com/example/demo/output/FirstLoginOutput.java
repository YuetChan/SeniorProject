package com.example.demo.output;

public class FirstLoginOutput {
	
	private boolean success;
	private String tokenString;
	
	public FirstLoginOutput(boolean success, String tokenString) {
		
		super();
		
		this.success = success;
		this.tokenString = tokenString;
		
	}

	public boolean isSuccessed() {
		
		return success;
		
	}

	public void setSuccessed(boolean success) {
		
		this.success = success;
		
	}

	public String getTokenString() {
		
		return tokenString;
		
	}

	public void setTokenString(String tokenString) {
		
		this.tokenString = tokenString;
		
	}
	
}
