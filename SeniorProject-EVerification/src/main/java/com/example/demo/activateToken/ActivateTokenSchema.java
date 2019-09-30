package com.example.demo.activateToken;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.example.demo.exceptions.DbException;
import com.example.demo.activateToken.ActivateToken;

public class ActivateTokenSchema implements IActivateTokenSchema {

    private MongoOperations mongoOperations;
    
	public ActivateTokenSchema(MongoOperations mongoOperations) {
		
		super();
		this.mongoOperations = mongoOperations;
		
	}
	
	@Override
	public ActivateToken save(ActivateToken tokenToBeSaved) {
		
		ActivateToken savedToken = null;
		
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
    			query, update, findAndModifyOptions, ActivateToken.class, "ActivateToken");
    	
    	if(savedToken.getUserId() == tokenToBeSaved.getUserId())
    		return savedToken;
    	else 
    		throw new DbException("Existed token");
    	
	}
	
	@Override
	public ActivateToken update(ActivateToken tokenToBeUpdated) {
		
		ActivateToken updatedToken = null;
		
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
    			query, update, findAndModifyOptions, ActivateToken.class, "ActivateToken");
    	
    	return updatedToken;
	}

	@Override
	public ActivateToken findByUserId(int userId) {
		
		ActivateToken foundToken = null;
		
		Query query = new Query();
  	  	query.addCriteria(Criteria.where("UserId").is(userId));
  	  	
  	  	foundToken 
  	  	= mongoOperations.findOne(
  			query, ActivateToken.class, "ActivateToken");
  	  	
  	  	return foundToken;
		
	}
	
	@Override
	public void deleteAll() {
		
    	mongoOperations.remove(new Query(), ActivateToken.class, "ActivateToken");
    	
	}
	
}
