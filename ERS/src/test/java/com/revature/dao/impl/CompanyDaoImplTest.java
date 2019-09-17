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

import com.revature.ers.dao.impl.CompanyDaoImpl;
import com.revature.ers.model.Company;
import com.revature.ers.util.ConnectionUtil;

public class CompanyDaoImplTest {
	
Company company = new Company("Company", "address");
	
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

	CompanyDaoImpl cdi = new CompanyDaoImpl();
	
	@Test
	public void getCompaniesTest() {
		
		int size = cdi.getCompanies().size();
		assertEquals(size, 3);
		
	}
	
	@Test
	public void getCompanyTest() {
		
		assertEquals(cdi.getCompany(1).getName(), "Revature");
		  
	}

	@Test 
	public void createCompanyTest() {
		
		cdi.createCompany(company);
		assertEquals(cdi.getCompanies().size(), 4);
		
	}

	@Test
	public void updateCompanyTest() {
		cdi.updateCompany(1, company);
		assertEquals(cdi.getCompany(1).getName(), "Company");
	}

	@Test
	public void deleteCompanyTest() {
		cdi.createCompany(company);
		cdi.deleteCompany(4);
		assertEquals(cdi.getCompanies().size(), 3);
	}
}
