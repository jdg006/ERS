package com.revature.services;

import java.util.List;

import com.revature.ers.dao.impl.ReimbursementDaoImpl;
import com.revature.ers.model.Reimbursement;

public class ReimbursementService {
	
	ReimbursementDaoImpl rdi = new ReimbursementDaoImpl();
	
	public boolean createReimbursement(Reimbursement reimbursement) {
		
		return rdi.createReimbursement(reimbursement);
		
	}
	
	public boolean deleteReimbursement(int id) {
		
		return rdi.deleteReimbursement(id);
		
	}
	
	public Reimbursement getReimbursement(int id) {
		
		return rdi.getReimbursement(id);
		
	}
	
	public boolean updateReimbursement(int id, Reimbursement reimbursement) {
		
		return rdi.updateReimbursement(id, reimbursement);
		
	}
	
	public List<Reimbursement> getReimbursements(){
		
		return rdi.getReimbursements();
		
	}
	
	public List<Reimbursement> getReimbursements(int userId){
		
		return rdi.getReimbursements(userId);
		
	}
	
	public List<Reimbursement> getReimbursementsByCompanyId(int companyId){
		
		return rdi.getReimbursementsByCompanyId(companyId);
		
	}
	
	public Reimbursement getLastCreatedReimbursement() {
		return rdi.getLastCreatedReimbursement();
	}

}
