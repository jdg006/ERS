package com.revature.ers.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.ers.dao.InfoDao;
import com.revature.ers.model.Info;
import com.revature.ers.util.ConnectionUtil;

public class InfoDaoImpl implements InfoDao {
	
	private static Logger log = Logger.getRootLogger();

	public List<Info> getAllInfo() {

		String sql = "select * from \"ERS\".info";
		
		List <Info> allInfo = new ArrayList<Info>();
		
		try (Connection c = ConnectionUtil.getConnection();
				Statement s = c.createStatement();
				ResultSet rs = s.executeQuery(sql);){
			
			while(rs.next()) {
				
				int id = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String phone = rs.getString("phone");
				String address = rs.getString("address");
				String position = rs.getString("position");
				String imgSrc = rs.getString("img_src");
				int userId = rs.getInt("user_id");
				
				Info info = new Info(id, firstName, lastName, phone, address, position, imgSrc, userId);
	
				allInfo.add(info);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return allInfo;
	}

	public Info getInfo(int id) {
		
		String sql = "select * from \"ERS\".info where id = ?";
		
		Info info = null;
		
		try(Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)){
			
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				
				 id = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String phone = rs.getString("phone");
				String address = rs.getString("address");
				String position = rs.getString("position");
				String imgSrc = rs.getString("img_src");
				int userId = rs.getInt("user_id");
				
				
				 info = new Info(id, firstName, lastName, phone, address, position, imgSrc, userId);
	
			}
			
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}

	public boolean createInfo(Info info) {
		
		String sql = "insert into \"ERS\".info (first_name, last_name, phone, address, position, img_src, user_id) values (?,?,?,?,?,?,?)";
		boolean wasCreated = false;
		
		try (Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)){
	
			ps.setString(1, info.getFirstName());
			ps.setString(2, info.getLastName());
			ps.setString(3, info.getPhone());
			ps.setString(4, info.getAddress());
			ps.setString(5, info.getPosition());
			ps.setString(6, info.getImgSrc());
			ps.setInt(7, info.getUserId());
			
			if (ps.executeUpdate() == 1) {
				wasCreated = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return wasCreated;
	}

	public boolean updateInfo(int id, Info info) {
		
		String sql = "update \"ERS\".info set first_name = ?, last_name = ?, phone = ?, address = ?, position = ?, img_src = ?, user_id = ? where id = ?";
		boolean wasUpdated = false;
		
		try (Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)){
	
			ps.setString(1, info.getFirstName());
			ps.setString(2, info.getLastName());
			ps.setString(3, info.getPhone());
			ps.setString(4, info.getAddress());
			ps.setString(5, info.getPosition());
			ps.setString(6, info.getImgSrc());
			ps.setInt(7, info.getUserId());
			ps.setInt(8, id);
			
			if (ps.executeUpdate() == 1) {
				wasUpdated = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return wasUpdated;
	}

	public boolean deleteInfo(int id) {
		
		String sql = "delete from  \"ERS\".info where id = ?";
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
	public Info getInfoByUserId(int userId) {
		
		String sql = "select * from \"ERS\".info where user_id = ?";
		
		Info info = null;
		
		try(Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)){
			
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				
				int id = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String phone = rs.getString("phone");
				String address = rs.getString("address");
				String position = rs.getString("position");
				String imgSrc = rs.getString("img_src");
			    userId = rs.getInt("user_id");
				
				 info = new Info(id, firstName, lastName, phone, address, position, imgSrc, userId);
	
			}
			
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}
	
}
