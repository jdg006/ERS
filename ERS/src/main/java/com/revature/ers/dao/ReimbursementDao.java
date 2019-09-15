package com.revature.ers.dao;

import java.util.List;

import com.revature.ers.model.Reimbursement;

public interface ReimbursementDao {
	
	public List<Reimbursement> getReimbursements();
	public List<Reimbursement> getReimbursements(int userId);
	public List<Reimbursement> getReimbursementsByCompanyId(int companyId);
	public Reimbursement getReimbursement(int id);
	public Reimbursement getLastCreatedReimbursement();
	public boolean createReimbursement(Reimbursement reimbursement);
	public boolean updateReimbursement(int id, Reimbursement reimbursement);
	public boolean deleteReimbursement(int id);
	

}
