package com.example.demo.user;

public interface IUserSchema {

	
	public User save(User user);
	
	public User update(User userToBeUpdated);

	public User findByUseremail(String useremail);
	
	public User findByUserId(int userId);
	
	public void deleteAll();
	
	public int getNextId();


}
