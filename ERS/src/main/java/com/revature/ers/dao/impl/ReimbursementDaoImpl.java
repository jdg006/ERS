package com.revature.ers.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.revature.ers.dao.ReimbursementDao;
import com.revature.ers.model.Reimbursement;
import com.revature.ers.util.ConnectionUtil;

public class ReimbursementDaoImpl implements ReimbursementDao {
	
	public List<Reimbursement>getReimbursements(int userId){
		String sql = "select * from \"ERS\".reimbursement where emp_id = ?";
		List <Reimbursement> reimbursements = new ArrayList<Reimbursement>();
		
		Reimbursement reimbursement = null;
		
		try(Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)){
			
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				
				int id = rs.getInt("id");
				int empId = rs.getInt("emp_id");
				int manId = rs.getInt("man_id");
				float amt = rs.getFloat("amt");
				String reason = rs.getString("reason");
				Date date = rs.getDate("date");
				boolean denied = rs.getBoolean("denied");
				boolean approved = rs.getBoolean("approved");
				
				reimbursement = new Reimbursement(id, empId ,manId, amt, reason, date.toLocalDate(), denied, approved);
				reimbursements.add(reimbursement);
			}
			
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reimbursements;
		
	}

	public List<Reimbursement> getReimbursements() {
		
		String sql = "select * from \"ERS\".reimbursement";
		
		List <Reimbursement> reimbursements = new ArrayList<Reimbursement>();
		
		try (Connection c = ConnectionUtil.getConnection();
				Statement s = c.createStatement();
				ResultSet rs = s.executeQuery(sql);){
			
			while(rs.next()) {
				
				int id = rs.getInt("id");
				int empId = rs.getInt("emp_id");
				int manId = rs.getInt("man_id");
				float amt = rs.getFloat("amt");
				String reason = rs.getString("reason");
				Date date = rs.getDate("date");
				boolean denied = rs.getBoolean("denied");
				boolean approved = rs.getBoolean("approved");
				
				Reimbursement reimbursement = new Reimbursement(id, empId ,manId, amt, reason, date.toLocalDate(), denied, approved);
	
				reimbursements.add(reimbursement);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return reimbursements;
	}

	public Reimbursement getReimbursement(int id) {
		String sql = "select * from \"ERS\".reimbursement where id = ?";
		
		Reimbursement reimbursement = null;
		
		try(Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)){
			
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				
				id = rs.getInt("id");
				int empId = rs.getInt("emp_id");
				int manId = rs.getInt("man_id");
				float amt = rs.getFloat("amt");
				String reason = rs.getString("reason");
				Date date = rs.getDate("date");
				boolean denied = rs.getBoolean("denied");
				boolean approved = rs.getBoolean("approved");
				
				reimbursement = new Reimbursement(id, empId ,manId, amt, reason, date.toLocalDate(), denied, approved);
				
			}
			
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reimbursement;
	}

	public boolean createReimbursement(Reimbursement reimbursement) {
		String sql = "insert into \"ERS\".reimbursement (emp_id ,man_id, amt, reason, date, denied, approved) values (?,?,?,?,?,?,?)";
		boolean wasCreated = false;
		
		try (Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)){
	
			ps.setInt(1, reimbursement.getEmpId());
			ps.setInt(2, reimbursement.getManId());
			ps.setFloat(3, reimbursement.getAmt());
			ps.setString(4, reimbursement.getReason());
			ps.setDate(5, java.sql.Date.valueOf(reimbursement.getDate()));
			ps.setBoolean(6, reimbursement.isDenied());
			ps.setBoolean(7, reimbursement.isApproved());
			
			if (ps.executeUpdate() == 1) {
				wasCreated = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return wasCreated;
	}

	public boolean updateReimbursement(int id, Reimbursement reimbursement) {
		String sql = "update \"ERS\".reimbursement set emp_id = ? ,man_id = ?, amt = ?, reason = ?, date = ?, denied = ?, approved = ? where id = ?";
		boolean wasUpdated = false;
		
		try (Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)){
	
			ps.setInt(1, reimbursement.getEmpId());
			ps.setInt(2, reimbursement.getManId());
			ps.setFloat(3, reimbursement.getAmt());
			ps.setString(4, reimbursement.getReason());
			ps.setDate(5, java.sql.Date.valueOf(reimbursement.getDate()));
			ps.setBoolean(6, reimbursement.isDenied());
			ps.setBoolean(7, reimbursement.isApproved());
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

	public boolean deleteReimbursement(int id) {
		
		String sql = "delete from  \"ERS\".reimbursement where id = ?";
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
	public List<Reimbursement> getReimbursementsByCompanyId(int companyId) {
		String sql = "select \"ERS\".reimbursement.id, \"ERS\".reimbursement.emp_id, \"ERS\".reimbursement.man_id, \"ERS\".reimbursement.amt,"
				+ " \"ERS\".reimbursement.reason, \"ERS\".reimbursement.\"date\", \"ERS\".reimbursement.denied, \"ERS\".reimbursement.approved"
				+ " from \"ERS\".reimbursement join \"ERS\".user on \"ERS\".reimbursement.emp_id = \"ERS\".user.id where company_id = ?";
		List <Reimbursement> reimbursements = new ArrayList<Reimbursement>();
		try(Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)){
			
			ps.setInt(1, companyId);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				
				int id = rs.getInt("id");
				int empId = rs.getInt("emp_id");
				int manId = rs.getInt("man_id");
				float amt = rs.getFloat("amt");
				String reason = rs.getString("reason");
				Date date = rs.getDate("date");
				boolean denied = rs.getBoolean("denied");
				boolean approved = rs.getBoolean("approved");
				
				Reimbursement reimbursement = new Reimbursement(id, empId ,manId, amt, reason, date.toLocalDate(), denied, approved);
				reimbursements.add(reimbursement);
			}
			
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reimbursements;
	}

	@Override
	public Reimbursement getLastCreatedReimbursement() {
		String sql = "select * from \"ERS\".reimbursement order by \"ERS\".reimbursement.id desc limit 1";
		Reimbursement reimbursement = null;
		try (Connection c = ConnectionUtil.getConnection();
				Statement s = c.createStatement();
				ResultSet rs = s.executeQuery(sql);){
			
			while(rs.next()) {
				
				int id = rs.getInt("id");
				int empId = rs.getInt("emp_id");
				int manId = rs.getInt("man_id");
				float amt = rs.getFloat("amt");
				String reason = rs.getString("reason");
				Date date = rs.getDate("date");
				boolean denied = rs.getBoolean("denied");
				boolean approved = rs.getBoolean("approved");
				
				reimbursement = new Reimbursement(id, empId ,manId, amt, reason, date.toLocalDate(), denied, approved);
	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return reimbursement;
	}

}
