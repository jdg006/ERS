package com.revature.ers.dao;

import java.util.List;

import com.revature.ers.model.Company;

public interface CompanyDao {
	
	public List<Company> getCompanies();
	public Company getCompany(int id);
	public boolean createCompany(Company company);
	public boolean updateCompany(int id, Company company);
	public boolean deleteCompany(int id);

}
