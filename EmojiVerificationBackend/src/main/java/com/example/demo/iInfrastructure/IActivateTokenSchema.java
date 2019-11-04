package com.example.demo.iInfrastructure;

import com.example.demo.domain.entity.ActivateToken;

public interface IActivateTokenSchema {

	public ActivateToken save(ActivateToken loginToken);
	
	public ActivateToken update(ActivateToken token);

	public ActivateToken findByUserId(int userId);
	
	public void deleteAll();
	
}
