package com.example.demo.loginToken;

import java.util.List;

import com.example.demo.exceptions.FakeDbException;

public class FakeLoginTokenSchema implements ILoginTokenSchema{
	
	List<LoginToken> loginTokens;
	
	@Override
	public LoginToken save(LoginToken token){
		int userId = token.getUserId();
		for(int i = 0; i < loginTokens.size(); i++) {
			if(userId == loginTokens.get(i).getUserId()) {
				throw new FakeDbException("token already existed");
			}
		}
		
		loginTokens.add(token);
		return token;
		
	}
	
	@Override
	public LoginToken update(LoginToken token){
		
		LoginToken retrievedToken = null;
		
		for (int i = 0; i < loginTokens.size(); i++){
			if(loginTokens.get(i).getUserId() == token.getUserId()){
				retrievedToken = loginTokens.get(i);
			}
		}
		retrievedToken.setExpirationDate(token.getExpirationDate());
		retrievedToken.setTokenString(token.getTokenString());
		
		return retrievedToken;
	}
	
	@Override
	public LoginToken findByUserId(int userId){
		
		LoginToken foundToken = null;
		
		for(int i = 0; i < loginTokens.size(); i++){
			if(loginTokens.get(i).getUserId() == userId){
				foundToken = loginTokens.get(i);
			}
		}
		
		return foundToken;
	}

	@Override
	public void deleteAll() {
		
	}

}
