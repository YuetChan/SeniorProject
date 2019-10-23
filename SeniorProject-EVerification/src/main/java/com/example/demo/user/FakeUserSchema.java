
package com.example.demo.user;


import java.util.ArrayList;
import java.util.List;

import com.example.demo.exceptions.*;

public class FakeUserSchema implements IUserSchema {
	
	private List <User> users;
	private int max = 0;
	
	
	public FakeUserSchema() {
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

	

	
}



