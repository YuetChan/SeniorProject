package com.example.demo.iInfrastructure;

import com.example.demo.domain.entity.PasswordResetToken;

public interface IPasswordResetTokenSchema {

	public PasswordResetToken save(PasswordResetToken passwordResetToken);
	
	public PasswordResetToken update(PasswordResetToken token);

	public PasswordResetToken findByUserId(int userId);
	
	public void deleteAll();

}
