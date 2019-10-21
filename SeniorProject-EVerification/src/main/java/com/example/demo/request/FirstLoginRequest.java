package com.example.demo.request;

public class FirstLoginRequest {
	
	private String useremail;
	private String password;
	
	public FirstLoginRequest(String useremail, String password) {
		
		super();
		
		this.useremail = useremail;
		this.password = password;
		
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
	
}
