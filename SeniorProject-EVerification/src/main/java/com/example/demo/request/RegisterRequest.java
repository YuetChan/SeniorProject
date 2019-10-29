package com.example.demo.request;

public class RegisterRequest {

	private String useremail;
	private String password;
	private String imageDataUrl;
	
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

	public String getImageDataUrl() {
		
		return imageDataUrl;
		
	}

	public void setImageDataUrl(String imageDataUrl) {
		
		this.imageDataUrl = imageDataUrl;
		
	}
	
}
