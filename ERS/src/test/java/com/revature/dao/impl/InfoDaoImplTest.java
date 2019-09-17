package com.revature.dao.impl;


import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.ers.dao.impl.InfoDaoImpl;
import com.revature.ers.model.Info;
import com.revature.ers.util.ConnectionUtil;


public class InfoDaoImplTest {
	
	Info info = new Info("a","a","1111111","210 street", "position", null, 3);
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	InfoDaoImpl idi = new InfoDaoImpl();
	@Test
	public void getAllInfoTest() {
		
		int size = idi.getAllInfo().size();
		assertEquals(size, 3);
		
	}
	@Test
	public void getInfoTest() {
		
		assertEquals(idi.getInfo(1).getFirstName(), "Joe");
		  
	}

	@Test 
	public void getInfoByUserIdTest() {
		assertEquals(idi.getInfo(1).getFirstName(), "Joe");
	}

	@Test 
	public void createInfoTest() {
		
		idi.createInfo(info);
		assertEquals(idi.getAllInfo().size(), 4);
		
	}

	@Test
	public void updateInfoTest() {
		idi.updateInfo(1, info);
		assertEquals(idi.getInfo(1).getFirstName(), "a");
	}

	@Test
	public void deleteInfoTest() {
		idi.deleteInfo(1);
		assertEquals(idi.getAllInfo().size(), 2);
	}
	
}
