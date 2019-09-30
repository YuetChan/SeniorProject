package com.example.demo.passwordResetToken;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.exceptions.FakeDbException;

public class FakePasswordResetTokenSchema implements IPasswordResetTokenSchema {
	
	List<PasswordResetToken> passwordResetTokens;
	
	public FakePasswordResetTokenSchema() {
		passwordResetTokens = new ArrayList();
	}
	
	@Override
	public PasswordResetToken save(PasswordResetToken token){
		int userId = token.getUserId();
		for(int i = 0; i < passwordResetTokens.size(); i++) {
			if(userId == passwordResetTokens.get(i).getUserId()) 
				throw new FakeDbException("token already existed");
		}
		
		passwordResetTokens.add(token);
		return token;
	}
	
	@Override
	public PasswordResetToken update(PasswordResetToken token){
		
		PasswordResetToken retrievedToken = null;
		
		for (int i = 0; i < passwordResetTokens.size(); i++){
			if(passwordResetTokens.get(i).getUserId() == token.getUserId())
				retrievedToken = passwordResetTokens.get(i);
		}
		retrievedToken.setExpirationDate(token.getExpirationDate());
		retrievedToken.setTokenString(token.getTokenString());
		
		return retrievedToken;
	}
	
	@Override
	public PasswordResetToken findByUserId(int userId){
		
		PasswordResetToken foundToken = null;
		
		for(int i = 0; i < passwordResetTokens.size(); i++){
			if(passwordResetTokens.get(i).getUserId() == userId){
				foundToken = passwordResetTokens.get(i);
			}
		}
		
		return foundToken;
	}
	
	@Override
	public void deleteAll() {
		return;
	}

}
