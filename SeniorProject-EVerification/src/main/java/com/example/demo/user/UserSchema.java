package com.example.demo.user;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


import java.util.ArrayList;
import java.util.List;

import com.example.demo.counter.Counter;
import com.example.demo.exceptions.*;

public class UserSchema implements IUserSchema{
	
    private MongoOperations mongoOperations;
	
	
	public UserSchema() {
		super();
		this.mongoOperations = mongoOperations;
	}
	
	@Override
	public User save(User user) {
		User savedPatient = null;
		
		Query query = new Query();
		Update update = new Update();
		FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions();
		
		user.setUserId(getNextId());
		
		query.addCriteria(Criteria.where("PatientID").is(user.getUserId()));

		update.setOnInsert("UserID", user.getUserId());
		update.setOnInsert("FirstName", user.getUseremail());
		update.setOnInsert("ImageReference", user.getImageReference());
		update.setOnInsert("Password", user.getPassword());
		update.setOnInsert("CreatedAt", user.getCreatedAt());
		update.setOnInsert("UpdatedAt", user.getUpdatedAt());
		
		findAndModifyOptions.upsert(true);
		findAndModifyOptions.returnNew(true);

		if(findByUserId(user.getUserId()) == null){
			savedPatient = mongoOperations.findAndModify(query, update, findAndModifyOptions, User.class, "User");
		}
		
		return savedPatient;	}
	
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
		query.addCriteria(Criteria.where("UserID").is(userId));

		foundUser = mongoOperations.findOne(query, User.class, "User");
		
		return foundUser;
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
