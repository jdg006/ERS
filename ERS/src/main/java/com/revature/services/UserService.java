package com.revature.services;

import java.util.List;

import com.revature.ers.dao.impl.UserDaoImpl;
import com.revature.ers.model.User;

public class UserService {
	
	UserDaoImpl udi = new UserDaoImpl();
	
	public boolean createUser(User user) {
		return udi.createUser(user);
	}
	
	public boolean deleteUsed(int id) {
		return udi.deleteUser(id);
	}
	
	public User getLastCreatedUser() {
		return udi.getLastCreatedUser();
	}
	
	public User getUser(int id) {
		return udi.getUser(id);
	}
	
	public User getUser(String username) {
		return udi.getUser(username);
	}
	
	public boolean updateUser(int id, User user) {
		return udi.updateUser(id, user);
	}
	
	public List<User> getUsers(){
		return udi.getUsers();
	}
	
	public List<User> getUsersByCompanyId(int companyId){
		return udi.getUsersByCompanyId(companyId);
	}

}
