package com.example.demo.loginToken;

public interface ILoginTokenSchema {

	public LoginToken save(LoginToken loginToken);
	
	public LoginToken update(LoginToken token);

	public LoginToken findByUserId(int userId);
	
	public void deleteAll();
	
}
