package com.example.demo.domain.iservice.service;

import com.example.demo.domain.entity.ActivateToken;
import com.example.demo.domain.entity.PasswordResetToken;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.value.RegisterDetail;
import com.example.demo.iInfrastructure.IActivateTokenSchema;
import com.example.demo.iInfrastructure.IPasswordResetTokenSchema;
import com.example.demo.iInfrastructure.IUserSchema;

public class UserService {
	
	public UserService() {
	}
	
	public boolean firstLogin(
			String useremail, String password, 
			IUserSchema userSchema) {
		
		boolean firstLogined = false;
		
		User user = userSchema.findByUseremail(useremail);
		if (user != null && password.equals(user.getPassword())) {
	
			if(user.isActivated() == true) 
				firstLogined = true;
			else
				firstLogined = false;
			
		}else 
			firstLogined = false;
		
		return firstLogined;
		
	}
	
	public boolean secondLogin() {
		
		return true;
		
	}
	
	public RegisterDetail register(
			String useremail, 
			IUserSchema userSchema, IActivateTokenSchema activateTokenSchema) {
		
		RegisterDetail registerDetail = new RegisterDetail();
		
		User user = userSchema.findByUseremail(useremail);
		if (user == null){
			
			registerDetail.setRegisterSuccess(true); 
			registerDetail.setRegisteredBefore(false);
			
		}else{
			
			ActivateToken activateToken = activateTokenSchema.findByUserId(user.getUserId());
			if(!user.isActivated() && activateToken.isExpired()){

				registerDetail.setRegisteredBefore(true);
				registerDetail.setRegisterSuccess(true);
				
			}else {
				
				registerDetail.setRegisteredBefore(true);
				registerDetail.setRegisterSuccess(false);
				
			}
			
		}

		return registerDetail;
		
	}
	
	public boolean resetPassword(
			String useremail, 
			IUserSchema userSchema, 
			IPasswordResetTokenSchema passwordResetTokenSchema) {
		
		boolean reset = false;
		
		User user = userSchema.findByUseremail(useremail);
		if(user != null && user.isActivated()) 
			reset = true;
		else 
			reset = false;
		
		return reset;
		
	}
	
	public boolean changePassword(
			int userId, String tokenString, 
			IPasswordResetTokenSchema passwordResetTokenSchema) {
		
		boolean changed = false;
		
		PasswordResetToken passwordResetToken = passwordResetTokenSchema.findByUserId(userId);
		if(passwordResetToken != null 
				&& passwordResetToken.getTokenString().equals(tokenString) 
				&& !passwordResetToken.isExpired()) 
			changed = true;
		else 
			changed = false;
		
		return changed;
		
	}
	
}

