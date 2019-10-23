package com.example.demo.user;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.demo.passwordResetToken.IPasswordResetTokenSchema;
import com.example.demo.passwordResetToken.PasswordResetToken;
import com.example.demo.passwordResetToken.PasswordResetTokenSchema;
import com.example.demo.registerDetail.RegisterDetail;
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
	
	public RegisterDetail register(
			String useremail, 
			UserSchema userSchema) {
		
		User user = userSchema.findByUseremail(useremail);
		RegisterDetail detail = new RegisterDetail();
		
		//CHECK ACTIVATE TOKEN ONCE THAT CLASS EXISTS
		
		if (user == null){
			detail.setRegisterSuccess(true); 
			detail.setRegisteredBefore(false);
		}
		
		else{
			if(!user.isActivated()){
				detail.setRegisteredBefore(true);
				detail.setRegisterSuccess(true);
			}
			else{
				detail.setRegisteredBefore(true);
				detail.setRegisterSuccess(false);
			}
		}

		return detail;
		
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

