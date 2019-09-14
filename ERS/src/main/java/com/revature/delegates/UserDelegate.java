package com.revature.delegates;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
		int userId = us.getLastCreatedUser().getId();
		
		User user = new User(email, password, permissionLevel, 0, companyId, false);
		Info info = new Info(firstName, lastName, phone, address, position, " ", userId);
		boolean userCreated = us.createUser(user);
		boolean infoCreated = is.createInfo(info);
		
		if (userCreated && infoCreated) {
			response.setStatus(200);
		}
		else {
			response.setStatus(500);
		}
		
	}
	
	public void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		String userJSON = request.getReader().readLine();
		ObjectMapper om = new ObjectMapper();
		User user = om.readValue(userJSON, User.class);
		boolean updated = us.updateUser(user.getId(), user);
		
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
	
}
