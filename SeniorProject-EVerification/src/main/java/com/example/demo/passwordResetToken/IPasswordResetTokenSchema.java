package com.example.demo.passwordResetToken;

public interface IPasswordResetTokenSchema {

	public PasswordResetToken save(PasswordResetToken passwordResetToken);
	
	public PasswordResetToken update(PasswordResetToken token);

	public PasswordResetToken findByUserId(int userId);
	
	public void deleteAll();

}
