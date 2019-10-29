package com.example.demo.user;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.example.demo.counter.Counter;
import com.example.demo.exceptions.DbException;

public class UserSchema implements IUserSchema{
	
    private MongoOperations mongoOperations;
	
	
	public UserSchema(MongoOperations mongoOperations) {
		
		super();
		this.mongoOperations = mongoOperations;
		
	}
	
	@Override
	public User save(User userToBeSaved) {
		
		User savedUser = null;
		
		Query query = new Query();
		Update update = new Update();
		FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions();
		
		query.addCriteria(Criteria.where("UserId").is(userToBeSaved.getUserId()));

		update.setOnInsert("UserId", userToBeSaved.getUserId());
		update.setOnInsert("Useremail", userToBeSaved.getUseremail());
		update.setOnInsert("Password", userToBeSaved.getPassword());
		update.setOnInsert("ImageReference", userToBeSaved.getImageReference());
		
		update.setOnInsert("Activated", userToBeSaved.isActivated());
		update.setOnInsert("CreatedAt", userToBeSaved.getCreatedAt());
		update.setOnInsert("UpdatedAt", userToBeSaved.getUpdatedAt());
		
		findAndModifyOptions.upsert(true);
		findAndModifyOptions.returnNew(true);

	  	savedUser 
    	= mongoOperations.findAndModify(
    			query, update, findAndModifyOptions, User.class, "User");
	  	
    	if(savedUser.getUserId() == userToBeSaved.getUserId())
    		return savedUser;
    	else 
    		throw new DbException("Existed User");
				
	}
	
	@Override
	public User update(User userToBeUpdated) {
		
		User updatedUser = null;
		
		Query query = new Query();
		Update update = new Update();
		FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions();

		query.addCriteria(Criteria
				.where("userId").is(userToBeUpdated.getUserId()));
    	
		update.set("Useremail", userToBeUpdated.getUseremail());
		update.set("Password", userToBeUpdated.getPassword());
		update.set("ImageReference", userToBeUpdated.getImageReference());
		
		update.set("Activated", userToBeUpdated.isActivated());
		update.set("CreatedAt", userToBeUpdated.getCreatedAt());
		update.set("UpdatedAt", userToBeUpdated.getUpdatedAt());
		
		findAndModifyOptions.upsert(true);
		findAndModifyOptions.returnNew(true);
		
		updatedUser 
    	= mongoOperations.findAndModify(
    			query, update, findAndModifyOptions, User.class, "User");
    	
    	return updatedUser;
	}

	
	@Override
	public User findByUseremail(String useremail) {
		
		User foundUser = null;
		
		Query query = new Query();
		query.addCriteria(Criteria.where("Useremail").is(useremail));

		foundUser = mongoOperations.findOne(query, User.class, "User");
		
		return foundUser;
		
	}

	@Override
	public User findByUserId(int userId) {
		
		User foundUser = null;
		
		Query query = new Query();
		query.addCriteria(Criteria.where("UserId").is(userId));

		foundUser = mongoOperations.findOne(query, User.class, "User");
		
		return foundUser;
	}
	
	@Override
	public void deleteAll() {
		
    	mongoOperations.remove(new Query(), User.class, "User");
    	mongoOperations.remove(new Query(), "UserCounter");
    	
	}
	
	@Override
	public int getNextId() {
		
		Query query = new Query();
	  	query.addCriteria(Criteria.where("_id").is("User"));
			
	  	Update update = new Update();
	 
	  	FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions();
	  	findAndModifyOptions.upsert(true);
	  	findAndModifyOptions.returnNew(true);
	  	  
	  	Counter counter 
	  	= mongoOperations.findAndModify(
	  			query, update.inc("seq", 1), findAndModifyOptions, Counter.class, "UserCounter");
	    
	  	return counter.getSeq();
	        
	}
	
}
