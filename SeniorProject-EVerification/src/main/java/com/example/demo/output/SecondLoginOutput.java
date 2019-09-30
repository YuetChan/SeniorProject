package com.example.demo.output;

public class SecondLoginOutput {
	
	private boolean success;
	
	public SecondLoginOutput(boolean success) {
		
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
