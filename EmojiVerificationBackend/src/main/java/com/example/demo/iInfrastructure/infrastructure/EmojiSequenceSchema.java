package com.example.demo.iInfrastructure.infrastructure;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.example.demo.domain.entity.EmojiSequence;
import com.example.demo.iIfrastructure.infrastructure.exceptions.DbException;
import com.example.demo.iInfrastructure.IEmojiSequenceSchema;

public class EmojiSequenceSchema implements IEmojiSequenceSchema {

	
    private MongoOperations mongoOperations;
    
	public EmojiSequenceSchema(MongoOperations mongoOperations) {
		
		super();
		this.mongoOperations = mongoOperations;
		
	}
	
	@Override
	public EmojiSequence save(EmojiSequence sequenceToBeSaved) {
		
		EmojiSequence savedToken = null;
		
		Query query = new Query();
		query.addCriteria(Criteria
				.where("UserId").is(sequenceToBeSaved.getUserId()));
    	
		Update update = new Update();
		update.setOnInsert("UserId", sequenceToBeSaved.getUserId());
		update.setOnInsert("EmojiSequenceKeys", sequenceToBeSaved.getEmojiSequenceKeys());
		update.setOnInsert("ExpirationDate", sequenceToBeSaved.getExpirationDate());
		
		FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions();
		findAndModifyOptions.upsert(true);
		findAndModifyOptions.returnNew(true);
		
    	savedToken 
    	= mongoOperations.findAndModify(
    			query, update, findAndModifyOptions, EmojiSequence.class, "EmojiSequence");
    	
    	if(savedToken.getUserId() == sequenceToBeSaved.getUserId())
    		return savedToken;
    	else 
    		throw new DbException("Existed sequence");
    	
	}
	
	@Override
	public EmojiSequence update(EmojiSequence sequenceToBeUpdated) {
		
		EmojiSequence updatedToken = null;
		
		Query query = new Query();
		query.addCriteria(Criteria
				.where("UserId").is(sequenceToBeUpdated.getUserId()));
    	
		Update update = new Update();
		update.set("EmojiSequenceKeys", sequenceToBeUpdated.getEmojiSequenceKeys());
		update.set("ExpirationDate", sequenceToBeUpdated.getExpirationDate());
		
		FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions();
		findAndModifyOptions.upsert(true);
		findAndModifyOptions.returnNew(true);
		
    	updatedToken 
    	= mongoOperations.findAndModify(
    			query, update, findAndModifyOptions, EmojiSequence.class, "EmojiSequence");
    	
    	return updatedToken;
	}

	@Override
	public EmojiSequence findByUserId(int userId) {
		
		EmojiSequence foundToken = null;
		
		Query query = new Query();
  	  	query.addCriteria(Criteria.where("UserId").is(userId));
  	  	
  	  	foundToken 
  	  	= mongoOperations.findOne(
  			query, EmojiSequence.class, "EmojiSequence");
  	  	
  	  	return foundToken;
		
	}
	
	@Override
	public void deleteAll() {
		
    	mongoOperations.remove(new Query(), EmojiSequence.class, "EmojiSequence");
    	
	}

	
}
