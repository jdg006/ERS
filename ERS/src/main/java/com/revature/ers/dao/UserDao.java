package com.revature.ers.dao;

import java.util.List;

import com.revature.ers.model.User;

public interface UserDao {
	
	public List<User> getUsers();
	public List<User> getUsersByCompanyId(int companyId);
	public User getUser(int id);
	public User getUser(String username);
	public User getLastCreatedUser();
	public boolean createUser(User user);
	public boolean updateUser(int id, User user);
	public boolean deleteUser(int id);

}
