package com.revature.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.revature.delegates.AuthDelegate;
import com.revature.delegates.CompanyDelegate;
import com.revature.delegates.InfoDelegate;
import com.revature.delegates.ReimbursementDelegate;
import com.revature.delegates.UserDelegate;
//import com.revature.delegates.DepartmentDelegate;
//import com.revature.delegates.EmployeeDelegate;
import com.revature.delegates.ViewDelegate;

public class RequestHelper {
	
	private ViewDelegate vd = new ViewDelegate();
	private CompanyDelegate cd = new CompanyDelegate();
	private UserDelegate ud = new UserDelegate();
	private ReimbursementDelegate rd = new ReimbursementDelegate();
	private InfoDelegate id = new InfoDelegate();
	private AuthDelegate ad = new AuthDelegate();
	private static Logger log = Logger.getRootLogger();
	
	public void processGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		log.info(request.getRequestURI());
		String uri = request.getServletPath();
		if(request.getRequestURI().matches("/ERS/api/companies") || uri.startsWith("/api/") && ad.isAuthorized(request)) {
			String record = uri.substring(5);
			switch(record) {
			case "companies":
				cd.getCompanies(request, response);
				break;
			case "users":
				ud.getUsers(request, response);
				break;
			case "reimbursements":
				rd.getReimbursements(request, response);
				break;
			case "all_info":
				id.getAllInfo(request, response);
				break;
			case "set_employee":
				id.getInfoById(request, response);
				break;
			case "emp_reimbursements":
				rd.getUserReimbursements(request, response);
				break;
			case "last_reimbursement":
				rd.getLastReimbursement(request, response);
				break;
			case "company_reimbursements":
				rd.getReimbursementsByCompanyId(request, response);
				break;
			case "company_employees":
				ud.getAllCompanyEmployees(request, response);
				break;
			
			default:
				response.sendError(404, "record not supported");
			}
		} else {
			
			vd.returnView(request, response);
		}
	}
	
	public void processPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String path = request.getServletPath();
		switch(path) {
		case "/login":
			ad.authenticate(request, response);
			break;
		case "/new_account":
			ud.createUser(request, response);
			break;
		case "/new_reimbursement":
			rd.createRiembursement(request, response);
			break;
		default:
			response.sendError(405);
		}
	}
	
	public void processPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String path = request.getServletPath();
		switch(path) {
		case "/update_info":
			
			if(ad.isAuthorized(request)) {
			id.updateInfo(request, response);
			}
			else {
				response.setStatus(401);
			}
			break;
		case "/update_reimbursement":
			rd.updateReimbursement(request, response);
			break;
		case "/update_user":
			ud.updateUser(request, response);
			break;
		default:
			response.sendError(405);
		}
	}
	
	public void processDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String path = request.getServletPath();
		switch(path) {
		case "/delete_user":
			ud.deleteUser(request, response);
			break;
		default:
			response.sendError(405);
		}
	} 

}