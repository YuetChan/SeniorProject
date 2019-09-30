package com.example.demo.request;

public class RegisterRequest {
	
	private String useremail;
	private String password;
	
	private String userImgBase64;

	public RegisterRequest(String useremail, String password, String userImgBase64) {
		
		super();
		
		this.useremail = useremail;
		this.password = password;
		
		this.userImgBase64 = userImgBase64;
		
	}

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

	public String getUserImgBase64() {
		
		return userImgBase64;
		
	}

	public void setUserImgBase64(String userImgBase64) {
		
		this.userImgBase64 = userImgBase64;
		
	}
	
	

}
