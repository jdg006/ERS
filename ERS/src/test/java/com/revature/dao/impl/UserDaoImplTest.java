package com.revature.dao.impl;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.ers.dao.impl.UserDaoImpl;
import com.revature.ers.model.User;
import com.revature.ers.util.ConnectionUtil;

public class UserDaoImplTest {
	
	@Before
	public void setUp() throws SQLException, FileNotFoundException {
		try(Connection c = ConnectionUtil.getConnection();){
			RunScript.execute(c, new FileReader("setup.sql"));
		}
	}
	
	@After
	public void truncate() {
		String sql = "truncate account,user_id_account_id,user_info";
		
		try (Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);)
		{
			ps.executeUpdate();

		}
		 catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	UserDaoImpl udi = new UserDaoImpl();
	
	User user = new User("email@email.com", "password", 0, 1, 1, false);
	
	@Test
	public void createUserTest () {
		udi.createUser(user);
		assertEquals(udi.getUsers().size(), 4);
	}
	@Test
	public void deleteUserTest () {
		udi.createUser(user);
		udi.deleteUser(4);
		assertEquals(udi.getUsers().size(), 3);
	}
	@Test
	public void getUsersTest () {
		assertEquals(udi.getUsers().size(), 3);
	}
	@Test
	public void getUserTest () {
		assertEquals(udi.getUser(1).getEmail(), "j@j.com");
	}
	@Test
	public void updateUserTest () {
		
		udi.updateUser(1, user);
		assertEquals(udi.getUser(1).getEmail(), "email@email.com");
		
	}
	@Test
	public void getUsersByCompanyIdTest() {
		assertEquals(udi.getUsersByCompanyId(1).size(), 3);
		assertEquals(udi.getUsersByCompanyId(2).size(), 0);
	}
	@Test
	public void getUserByUsernameTest() {
		assertEquals(udi.getUser("j@j.com").getId(), 1);
	}
	@Test
	public void getLastCreatedUser() {
		assertEquals(udi.getLastCreatedUser().getId(), 3);
	}
}
