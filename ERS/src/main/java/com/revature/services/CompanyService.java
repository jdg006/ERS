package com.revature.services;

import java.util.List;

import com.revature.ers.dao.impl.CompanyDaoImpl;
import com.revature.ers.model.Company;

public class CompanyService {
	
	CompanyDaoImpl cdi = new CompanyDaoImpl();
	
	public boolean createCompany(Company company) {
		
		return cdi.createCompany(company);
		
	}
	
	public boolean deleteCompany(int id) {
		
		return cdi.deleteCompany(id);
		
	}
	
	public Company getCompany(int id) {
		
		return cdi.getCompany(id);
		
	}
	
	public boolean updateCompnay(int id, Company company) {
		
		return cdi.updateCompany(id, company);
		
	}
	
	public List<Company> getCompanies(){
		
		return cdi.getCompanies();
		
	}

}
