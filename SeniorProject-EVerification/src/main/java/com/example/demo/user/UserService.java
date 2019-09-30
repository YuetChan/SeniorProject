package com.example.demo.user;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.demo.passwordResetToken.IPasswordResetTokenSchema;
import com.example.demo.passwordResetToken.PasswordResetToken;
import com.example.demo.passwordResetToken.PasswordResetTokenSchema;
import com.example.demo.loginToken.LoginToken;

public class UserService {
	
	public UserService() {
	}
	
	public LoginToken firstLogin(String useremail, String password, UserSchema userSchema) {
		
		return null;
		
	}
	
	public boolean secondLogin() {
		
		return true;
		
	}
	
	public boolean register(String useremail, String password) {
		
		return true;
		
	}
	
	public boolean resetPassword(
			String useremail, 
			UserSchema userSchema, 
			IPasswordResetTokenSchema passwordResetTokenSchema) {
		
		boolean reset = false;
		
		User user = userSchema.findByUseremail(useremail);
		if(user != null) {
			int userId = user.getUserId();
			PasswordResetToken passwordResetToken = passwordResetTokenSchema.findByUserId(userId);
			if(passwordResetToken != null)
				reset = true;
			else
				reset = false;
		}else 
			reset = false;
		
		return reset;
		
	}
	
	public boolean changePassword(
			int userId, String tokenString, 
			UserSchema userSchema, 
			IPasswordResetTokenSchema passwordResetTokenSchema) {
		
		boolean changed = false;
		
		PasswordResetToken passwordResetToken = passwordResetTokenSchema.findByUserId(userId);
		if(passwordResetToken.getTokenString().equals(tokenString) && !passwordResetToken.isExpired()) {
			changed = true;
		}else 
			changed = false;
		
		return changed;
		
	}
	
}

