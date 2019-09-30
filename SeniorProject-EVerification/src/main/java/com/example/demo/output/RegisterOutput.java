package com.example.demo.output;

public class RegisterOutput {
	
	private boolean success;
	
	public RegisterOutput(boolean success) {
		
		super();
		
		this.success = success;
		
	}

	public boolean isSuccess() {
		
		return success;
		
	}

	public void setSuccess(boolean success) {
		
		this.success = success;
		
	}
	
}
