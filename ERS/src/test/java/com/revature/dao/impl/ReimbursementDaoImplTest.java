package com.revature.dao.impl;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.ers.dao.impl.ReimbursementDaoImpl;
import com.revature.ers.model.Reimbursement;
import com.revature.ers.util.ConnectionUtil;

public class ReimbursementDaoImplTest {
	
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
	
	LocalDate date = LocalDate.of(2000, 1, 4);
	
	Reimbursement reimbursement = new Reimbursement(1, 1, 3, "reason", date, false, false);
	
	ReimbursementDaoImpl rdi = new ReimbursementDaoImpl();
	
	@Test
	public void createReimbursementTest () {
		
		rdi.createReimbursement(reimbursement);
		assertEquals(rdi.getReimbursements().size(), 4);
		
	}
	
	@Test
	public void deleteReimbursementTest () {
		
		rdi.deleteReimbursement(1);
		assertEquals(rdi.getReimbursements().size(), 2);
		
	}
	
	@Test
	public void getLastCreatedReimbursementTest () {
		
		assertEquals(rdi.getLastCreatedReimbursement().getId(),3);
		
	}
	
	@Test
	public void getReimbursementTest () {
		
		assertEquals(rdi.getReimbursement(1).getId(), 1);
		
	}
	
	@Test
	public void getReimbursementsTest () {
		
		assertEquals(rdi.getReimbursements().size(), 3);
		
	}
	
	@Test
	public void getReimbursementsByUserIdTest () {
		
		assertEquals(rdi.getReimbursements(1).size(), 1);
		
	}
	
	@Test
	public void getReimbursementsByCompanyIdTest () {
		
		assertEquals(rdi.getReimbursementsByCompanyId(1).size(), 3);
		
	}
	
	@Test
	public void updateReimbursementsTest () {
		
		rdi.updateReimbursement(1, reimbursement);
		assertEquals(rdi.getReimbursement(1).getReason(),"reason");
		
	}
	
}
