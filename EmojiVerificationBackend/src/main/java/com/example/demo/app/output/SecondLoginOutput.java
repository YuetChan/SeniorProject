package com.example.demo.app.output;

import java.util.List;

public class SecondLoginOutput {
	
	private String status;
	private List<String> errors;
	
	public SecondLoginOutput() {
		
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
	
}
