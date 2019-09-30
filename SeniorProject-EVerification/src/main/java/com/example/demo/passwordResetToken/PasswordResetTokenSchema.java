package com.example.demo.passwordResetToken;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.example.demo.exceptions.DbException;
import com.example.demo.passwordResetToken.PasswordResetToken;

public class PasswordResetTokenSchema implements IPasswordResetTokenSchema {
	
    private MongoOperations mongoOperations;
    
	public PasswordResetTokenSchema(MongoOperations mongoOperations) {
		
		super();
		this.mongoOperations = mongoOperations;
		
	}
	
	@Override
	public PasswordResetToken save(PasswordResetToken tokenToBeSaved) {
		
		PasswordResetToken savedToken = null;
		
		Query query = new Query();
		query.addCriteria(Criteria
				.where("userId").is(tokenToBeSaved.getUserId()));
    	
		Update update = new Update();
		update.setOnInsert("UserId", tokenToBeSaved.getUserId());
		update.setOnInsert("TokenString", tokenToBeSaved.getTokenString());
		update.setOnInsert("ExpirationDate", tokenToBeSaved.getExpirationDate());
		
		FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions();
		findAndModifyOptions.upsert(true);
		findAndModifyOptions.returnNew(true);
		
    	savedToken 
    	= mongoOperations.findAndModify(
    			query, update, findAndModifyOptions, PasswordResetToken.class, "PasswordResetToken");
    	
    	if(savedToken.getUserId() == tokenToBeSaved.getUserId())
    		return savedToken;
    	else 
    		throw new DbException("Existed token");
    	
	}

	@Override
	public PasswordResetToken update(PasswordResetToken tokenToBeUpdated) {
		
		PasswordResetToken updatedToken = null;
		
		Query query = new Query();
		query.addCriteria(Criteria
				.where("userId").is(tokenToBeUpdated.getUserId()));
    	
		Update update = new Update();
		update.set("TokenString", tokenToBeUpdated.getTokenString());
		update.set("ExpirationDate", tokenToBeUpdated.getExpirationDate());
		
		FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions();
		findAndModifyOptions.upsert(true);
		findAndModifyOptions.returnNew(true);
		
    	updatedToken 
    	= mongoOperations.findAndModify(
    			query, update, findAndModifyOptions, PasswordResetToken.class, "PasswordResetToken");
    	
    	return updatedToken;
		
	}
	
	@Override
	public PasswordResetToken findByUserId(int userId) {
		
		PasswordResetToken foundToken = null;
		
		Query query = new Query();
  	  	query.addCriteria(Criteria.where("UserId").is(userId));
  	  	
  	  	foundToken 
  	  	= mongoOperations.findOne(
  			query, PasswordResetToken.class, "PasswordResetToken");
  	  	
  	  	return foundToken;
		
	}
	
	@Override
	public void deleteAll() {
		
    	mongoOperations.remove(new Query(), PasswordResetToken.class, "PasswordResetToken");
    	
	}

}
