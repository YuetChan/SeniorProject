package com.example.demo.user;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.exceptions.*;

public class UserSchema {
	
	private List <User> users;
	private int max = 0;
	
	public UserSchema() {
		users = new ArrayList();
	}
	
	public User save(User user) {
		String useremail = user.getUseremail();
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getUseremail().equals(useremail)) {
				throw new FakeDbException("user already existed");
			}
		}
		
		users.add(user);
		return user;
	}

	public User findByUseremail(String useremail) {
		User foundUser = null;
		for( int i = 0; i < users.size(); i++) {
			if(users.get(i).getUseremail().equals(useremail))
				foundUser = users.get(i);
		}
		
		return foundUser;
	}
	
	public User findByUserId(int userId) {
		User foundUser = null;
		for( int i = 0; i < users.size(); i++) {
			if(users.get(i).getUserId() == userId)
				foundUser = users.get(i);
		}
		
		return foundUser;
	}
	
	public int getNextId() {
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getUserId() > max) {
				max = users.get(i).getUserId();
			}
		}
		
		max = max + 1;
		return max;
	}
	
	
                
	//When you implement the actual UserSchema use the following code for getNextId(). 
	//The rest of the schema operations should be similar to LoginTokenSchema and PasswordResetTokenSchema
	
	//public int getNextId() {
		
	//	Query query = new Query();
  	//  	query.addCriteria(Criteria.where("_id").is("User"));
		
  	//  	Update update = new Update();
 
  	//  	FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions();
  	//    findAndModifyOptions.upsert(true);
  	//    findAndModifyOptions.returnNew(true);
  	  
  	//  	Counter counter 
  	//  	= mongoOperations.findAndModify(
  	//  			query, update.inc("seq", 1), findAndModifyOptions, Counter.class, "UserCounter");
    //    return counter.getSeq();
        
    //}
	
}
