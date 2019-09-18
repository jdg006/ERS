package com.revature.delegates;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.model.Info;
import com.revature.ers.model.Reimbursement;
import com.revature.ers.model.User;
import com.revature.services.InfoService;
import com.revature.services.ReimbursementService;
import com.revature.services.UserService;

public class ReimbursementDelegate {
	
	ReimbursementService rs = new ReimbursementService();
	UserService us = new UserService();
	InfoService is = new InfoService();
	
	public void getReimbursements(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		List<Reimbursement> reimbursements = rs.getReimbursements();
		
		try(PrintWriter pw = response.getWriter()){
			pw.write(new ObjectMapper().writeValueAsString(reimbursements));
		}
	}
	
	public void getUserReimbursements(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		String token = request.getHeader("Authorization");
		String[] arrTok = token.split(":");
		String username = arrTok[0];
		User user = us.getUser(username);
		List<Reimbursement> reimbursements = rs.getReimbursements(user.getId());
		
		try(PrintWriter pw = response.getWriter()){
			pw.write(new ObjectMapper().writeValueAsString(reimbursements));
		}
	}
	
	public void getLastReimbursement(HttpServletRequest request, HttpServletResponse response) throws IOException {

		Reimbursement reimbursement = rs.getLastCreatedReimbursement();
		
		try(PrintWriter pw = response.getWriter()){
			pw.write(new ObjectMapper().writeValueAsString(reimbursement));
		}
		
	}
	
	public void getReimbursementsByCompanyId(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String token = request.getHeader("Authorization");
		String[] arrTok = token.split(":");
		String username = arrTok[0];
		User user = us.getUser(username);
		
		List<Info> managersInfo = new ArrayList<Info>();
		List<User> managers = new ArrayList<User>();
		List<User> users = us.getUsersByCompanyId(user.getCompanyId());
		for (User u : users) {
			if (u.getPermissionLevel() > 0) {
				managers.add(u);
			}
		}
		
		for (User man : managers) {
			Info info = is.getInfoByUserId(man.getId());
			managersInfo.add(info);
		}
		
		int companyId = user.getCompanyId();
		List<Reimbursement> reimbursements = rs.getReimbursementsByCompanyId(companyId);
		
		try(PrintWriter pw = response.getWriter()){
			pw.write("[");
			pw.write(new ObjectMapper().writeValueAsString(reimbursements));
			pw.write(",");
			pw.write(new ObjectMapper().writeValueAsString(managersInfo));
			pw.write("]");
		}
	}
	
	public void createRiembursement(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String token = request.getHeader("Authorization");
		String[] arrTok = token.split(":");
		String username = arrTok[0];
		
		User user = us.getUser(username);
		
		int empId = user.getId();
		int manId = user.getSuperiorId();
		float amt = Float.parseFloat(request.getParameter("amt"));
		String reason = request.getParameter("reason");
		LocalDate date = LocalDate.parse(request.getParameter("date"));
		
		Reimbursement reimbursement = new Reimbursement(empId, manId, amt, reason, date, false, false);
		boolean rCreated = rs.createReimbursement(reimbursement);
		
		if (rCreated) {
			response.setStatus(200);
		}
		else {
			response.setStatus(500);
		}
	}
	
	public void updateReimbursement(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String[] bodyArr = request.getReader().readLine().split(":");
		User man = us.getUser(bodyArr[2]);
		int id = Integer.parseInt(bodyArr[0]);
		String approveOrDeny = bodyArr[1];
		int manId = man.getId();
		Info manInfo = is.getInfoByUserId(man.getId());
		Reimbursement reimbursement = rs.getReimbursement(id);
		
		if(approveOrDeny.equalsIgnoreCase("app")) {
			reimbursement.setApproved(true);
		}
		else {
			reimbursement.setDenied(true);
		}
		
		reimbursement.setManId(manId);
		boolean updated = rs.updateReimbursement(id, reimbursement);
		
		if (updated) {
			try(PrintWriter pw = response.getWriter()){
				pw.write("[");
				pw.write(new ObjectMapper().writeValueAsString(reimbursement));
				pw.write(",");
				pw.write(new ObjectMapper().writeValueAsString(manInfo));	
				pw.write("]");
			}
			response.setStatus(200);
		}
		else {
			response.setStatus(500);
		}
	}
}
