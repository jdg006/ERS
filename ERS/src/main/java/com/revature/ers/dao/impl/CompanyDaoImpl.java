package com.revature.ers.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.ers.dao.CompanyDao;
import com.revature.ers.model.Company;
import com.revature.ers.util.ConnectionUtil;

public class CompanyDaoImpl implements CompanyDao {
	
	private static Logger log = Logger.getRootLogger();

	@Override
	public List<Company> getCompanies() {
			
			String sql = "select * from \"ERS\".company"; 
			
			List <Company> companies = new ArrayList<Company>();
			
			try (Connection c = ConnectionUtil.getConnection();
					Statement s = c.createStatement();
					ResultSet rs = s.executeQuery(sql);){
				
				while(rs.next()) {
					
					int id = rs.getInt("id");
					String name = rs.getString("name");
					String hq_address = rs.getString("hq_address");
					
					Company company = new Company(id, name, hq_address);
		
					companies.add(company);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return companies;
	}
	
	@Override
	public Company getCompany(int id) {
		
		String sql = "select * from \"ERS\".company where id = ?";
		
		Company company = null;
		
		try(Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)){
			
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				
				 id = rs.getInt("id");
				String name = rs.getString("name");
				String hqAddress = rs.getString("hq_address");
				 company = new Company(id, name, hqAddress);
	
			}
			
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return company;
	}

	@Override
	public boolean createCompany(Company company) {
		
		String sql = "insert into \"ERS\".company (name, hq_address) values (?,?)";
		boolean wasCreated = false;
		
		try (Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)){
	
			ps.setString(1, company.getName());
			ps.setString(2, company.getHqAddress());
			
			if (ps.executeUpdate() == 1) {
				wasCreated = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return wasCreated;
	}

	@Override
	public boolean updateCompany(int id, Company company) {
		
		String sql = "update \"ERS\".company set name = ?, hq_address = ? where id = ?";
		boolean wasUpdated = false;
		
		try (Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)){
	
			ps.setString(1, company.getName());
			ps.setString(2, company.getHqAddress());
			ps.setInt(3, id);
			
			if (ps.executeUpdate() == 1) {
				wasUpdated = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return wasUpdated;
	}

	@Override
	public boolean deleteCompany(int id) {
		
		String sql = "delete from  \"ERS\".company where id = ?";
		boolean wasDeleted = false;
		
		try (Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)){
			
			ps.setInt(1, id);
			
			if (ps.executeUpdate() == 1) {
				wasDeleted = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return wasDeleted;
	}

}
