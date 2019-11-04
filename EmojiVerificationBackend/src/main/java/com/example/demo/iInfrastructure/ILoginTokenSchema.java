package com.example.demo.iInfrastructure;

import com.example.demo.domain.entity.LoginToken;

public interface ILoginTokenSchema {

	public LoginToken save(LoginToken loginToken);
	
	public LoginToken update(LoginToken token);

	public LoginToken findByUserId(int userId);
	
	public LoginToken findByTokenString(String tokenString);
	
	public void deleteAll();
	
}
