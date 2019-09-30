package com.example.demo.activateToken;

public interface IActivateTokenSchema {

	public ActivateToken save(ActivateToken loginToken);
	
	public ActivateToken update(ActivateToken token);

	public ActivateToken findByUserId(int userId);
	
	public void deleteAll();
	
}
