package com.revature.delegates;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.model.Info;
import com.revature.ers.model.User;
import com.revature.services.InfoService;
import com.revature.services.UserService;

public class UserDelegate {
	
	UserService us = new UserService();
	InfoService is = new InfoService();
	
	public void getUsers(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		List<User> users = us.getUsers();
		
		try(PrintWriter pw = response.getWriter()){
			pw.write(new ObjectMapper().writeValueAsString(users));
		}
	}
	
	public void createUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		int companyId = Integer.parseInt(request.getParameter("companyId"));
		int permissionLevel = Integer.parseInt(request.getParameter("permissionLevel"));
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String position = request.getParameter("position");
		
		User user = new User(email, password, permissionLevel, 0, companyId, false);
		boolean userCreated = us.createUser(user);
		int userId = us.getLastCreatedUser().getId();
		Info info = new Info(firstName, lastName, phone, address, position, " ", userId);
		boolean infoCreated = is.createInfo(info);
		
		if (userCreated && infoCreated) {
			response.setStatus(200);
		}
		else {
			response.setStatus(500);
		}
		
	}
	
	public void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		String token = request.getHeader("Authorization");
		String email = token.split(":")[0];
		User manager = us.getUser(email);
		String[] bodyArr = request.getReader().readLine().split("&");
		boolean approved = Boolean.parseBoolean(bodyArr[0]);
		int id = Integer.parseInt(bodyArr[1]);
		User user = us.getUser(id);
		user.setApproved(approved);
		user.setSuperiorId(manager.getId());
		
		Info info = is.getInfoByUserId(user.getId());
		
		boolean updated = us.updateUser(user.getId(), user);
		
		try(PrintWriter pw = response.getWriter()){
			pw.write("[");
			pw.write(new ObjectMapper().writeValueAsString(user));
			pw.write(",");
			pw.write(new ObjectMapper().writeValueAsString(info));	
			pw.write("]");	
		}
		
		if (updated) {
			response.setStatus(200);
			
		}
		else {
			response.setStatus(500);
		}
	}
	
	public void getAllCompanyEmployees(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		String token = request.getHeader("Authorization");
		String[] arrTok = token.split(":");
		String username = arrTok[0];
		User manager = us.getUser(username);
		int companyId = manager.getCompanyId();
		
		List <Info> allInfo = new ArrayList<Info>();
		List <User> users = us.getUsersByCompanyId(companyId);
		
		for(User user : users) {
			allInfo.add(is.getInfoByUserId(user.getId()));
		}
		
		try(PrintWriter pw = response.getWriter()){
			pw.write("[");
			pw.write(new ObjectMapper().writeValueAsString(users));
			pw.write(",");
			pw.write(new ObjectMapper().writeValueAsString(allInfo));
			pw.write("]");
		}
	}
	
	public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		String[] bodyArr = request.getReader().readLine().split("=");
		int id = Integer.parseInt(bodyArr[1]);
		Info info = is.getInfoByUserId(id);
		boolean infoDeleted = is.deleteInfo(info.getId());
		boolean userDeleted = us.deleteUsed(id);
		
		if (infoDeleted && userDeleted) {
			response.setStatus(200);
		}
		else {
			response.setStatus(500);
		}
	}
	
}
