package com.example.demo.user;

import java.time.LocalDateTime;

public class User {
	
	private int userId;
	
	private String useremail;
	private String password;
	private String imageReference;
	private boolean activated;
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public String getUseremail() {
		
		return useremail;
		
	}
	
	public void setUseremail(String useremail) {
		
		this.useremail = useremail;
		
	}
	
	public String getPassword() {
		
		return password;
		
	}
	
	public void setPassword(String password) {
		
		this.password = password;
		
	}
	
	public int getUserId() {
		
		return userId;
		
	}
	
	public void setUserId(int userId) {
		
		this.userId = userId;
		
	}

	public String getImageReference() {
		
		return imageReference;
		
	}

	public void setImageReference(String imageReference) {
		
		this.imageReference = imageReference;
		
	}

	public boolean isActivated() {
		
		return activated;
		
	}

	public void setActivated(boolean activated) {
		
		this.activated = activated;
		
	}

	public LocalDateTime getCreatedAt() {
		
		return createdAt;
		
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		
		this.createdAt = createdAt;
		
	}

	public LocalDateTime getUpdatedAt() {
		
		return updatedAt;
		
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		
		this.updatedAt = updatedAt;
		
	}
	
}
