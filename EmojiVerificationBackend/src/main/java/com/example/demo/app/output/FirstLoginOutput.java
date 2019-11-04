package com.example.demo.app.output;

import java.util.List;

public class FirstLoginOutput {
	
	private String status;
	
	private List<String> errors;
	private int userId;
	private String tokenString;
	private List<String> emojiSequence;
	
	public FirstLoginOutput() {
		
		super();
		
	}

	public String getStatus() {
		
		return status;
		
	}

	public void setStatus(String status) {
		
		this.status = status;
		
	}
	
	public List<String> getErrors() {
		
		return errors;
		
	}

	public void setErrors(List<String> errors) {
		
		this.errors = errors;
		
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

	public List<String> getEmojiSequence() {
		
		return emojiSequence;
		
	}

	public void setEmojiSequence(List<String> emojiSequence) {
		
		this.emojiSequence = emojiSequence;
		
	}
	
}
