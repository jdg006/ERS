package com.revature.delegates;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.model.Company;
import com.revature.services.CompanyService;

public class CompanyDelegate {

	private CompanyService cs = new CompanyService();
	
	public void getCompanies(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		List<Company> companies = cs.getCompanies();
		
		try(PrintWriter pw = response.getWriter()){
			pw.write(new ObjectMapper().writeValueAsString(companies));
		}
	}
	
	
}
