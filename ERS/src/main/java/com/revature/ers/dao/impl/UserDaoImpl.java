package com.revature.ers.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.ers.dao.UserDao;
import com.revature.ers.model.User;
import com.revature.ers.util.ConnectionUtil;

public class UserDaoImpl implements UserDao {
	
	private static Logger log = Logger.getRootLogger();

	public List<User> getUsers() {
		
		String sql = "select * from \"ERS\".user";
		
		List <User> users = new ArrayList<User>();
		
		try (Connection c = ConnectionUtil.getConnection();
				Statement s = c.createStatement();
				ResultSet rs = s.executeQuery(sql);){
			
			while(rs.next()) {
				
				int id = rs.getInt("id");
				String email = rs.getString("email");
				String password = rs.getString("password");
				int permissionLevel = rs.getInt("permission_level");
				int superiorId = rs.getInt("superior_id");
				int companyId = rs.getInt("company_id");
				boolean approved = rs.getBoolean("approved");
				
				User user = new User(id, email, password, permissionLevel, superiorId, companyId, approved);
	
				users.add(user);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return users;
	}

	public User getUser(int id) {
		String sql = "select * from \"ERS\".user where id = ?";
		
		User user = null;
		
		try(Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)){
			
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				
				id = rs.getInt("id");
				String email = rs.getString("email");
				String password = rs.getString("password");
				int permissionLevel = rs.getInt("permission_level");
				int superiorId = rs.getInt("superior_id");
				int companyId = rs.getInt("company_id");
				boolean approved = rs.getBoolean("approved");
				
				user = new User(id, email, password, permissionLevel, superiorId, companyId, approved);
	
			}
			
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	public User getUser(String username) {
		String sql = "select * from \"ERS\".user where email = ?";
		
		User user = null;
		
		try(Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)){
			
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				
				int id = rs.getInt("id");
				String email = rs.getString("email");
				String password = rs.getString("password");
				int permissionLevel = rs.getInt("permission_level");
				int superiorId = rs.getInt("superior_id");
				int companyId = rs.getInt("company_id");
				boolean approved = rs.getBoolean("approved");
				
				user = new User(id, email, password, permissionLevel, superiorId, companyId, approved);
	
			}
			
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	public User getLastCreatedUser() {
		String sql = "select * from \"ERS\".user order by id desc limit 1";
		
		User user = null;
		
		try (Connection c = ConnectionUtil.getConnection();
				Statement s = c.createStatement();
				ResultSet rs = s.executeQuery(sql);){
			
			while(rs.next()) {
				
				int id = rs.getInt("id");
				String email = rs.getString("email");
				String password = rs.getString("password");
				int permissionLevel = rs.getInt("permission_level");
				int superiorId = rs.getInt("superior_id");
				int companyId = rs.getInt("company_id");
				boolean approved = rs.getBoolean("approved");
				
				user = new User(id, email, password, permissionLevel, superiorId, companyId, approved);
	
			}
			
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	public boolean createUser(User user) {
		String sql = "insert into \"ERS\".user (email, password, permission_level, superior_id, company_id, approved) values (?,?,?,?,?,?)";
		boolean wasCreated = false;
		
		try (Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)){
	
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getPassword());
			ps.setInt(3, user.getPermissionLevel());
			ps.setInt(4, user.getSuperiorId());
			ps.setInt(5, user.getCompanyId());
			ps.setBoolean(6, user.isApproved());
		
			
			if (ps.executeUpdate() == 1) {
				wasCreated = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return wasCreated;
	}

	public boolean updateUser(int id, User user) {
		String sql = "update \"ERS\".user set email = ?, password = ?, permission_level = ?, superior_id = ?, company_id = ?, approved = ? where id = ?";
		boolean wasUpdated = false;
		
		try (Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)){
	
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getPassword());
			ps.setInt(3, user.getPermissionLevel());
			ps.setInt(4, user.getSuperiorId());
			ps.setInt(5, user.getCompanyId());
			ps.setBoolean(6, user.isApproved());
			ps.setInt(7, id);
			
			if (ps.executeUpdate() == 1) {
				wasUpdated = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return wasUpdated;
	}

	public boolean deleteUser(int id) {
		String sql = "delete from  \"ERS\".user where id = ?";
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

	@Override
	public List<User> getUsersByCompanyId(int companyId) {
		
		String sql ="select * from \"ERS\".user where company_id = ?";
		List<User> users = new ArrayList<User>();
		User user = null;
		
		try(Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)){
			
			ps.setInt(1, companyId);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				
				int id = rs.getInt("id");
				String email = rs.getString("email");
				String password = rs.getString("password");
				int permissionLevel = rs.getInt("permission_level");
				int superiorId = rs.getInt("superior_id");
				companyId = rs.getInt("company_id");
				boolean approved = rs.getBoolean("approved");
				
				user = new User(id, email, password, permissionLevel, superiorId, companyId, approved);
				users.add(user);
			}
			
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}

}
