package com.example.demo.domain.value;

public class RegisterDetail {
	
	private boolean registerSuccess;
	private boolean registeredBefore;
	
	public boolean isRegisterSuccess() {
		return registerSuccess;
	}
	
	public void setRegisterSuccess(boolean registerSuccess) {
		this.registerSuccess = registerSuccess;
	}
	
	public boolean isRegisteredBefore() {
		return registeredBefore;
	}
	
	public void setRegisteredBefore(boolean registeredBefore) {
		this.registeredBefore = registeredBefore;
	}

}
