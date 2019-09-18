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

public class InfoDelegate {
	
	InfoService is = new InfoService();
	UserService us = new UserService();
	private String token;
	
	public void getAllInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		List<Info> allInfo = is.getAllInfo();
		
		try(PrintWriter pw = response.getWriter()){
			pw.write(new ObjectMapper().writeValueAsString(allInfo));
		}
	}
	
	public void getInfoById(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		token = request.getHeader("Authorization");
		String[] tokenArr = token.split(":");
		int id = us.getUser(tokenArr[0]).getId();
		
		Info info = is.getInfoByUserId(id);
		
		try(PrintWriter pw = response.getWriter()){
			pw.write(new ObjectMapper().writeValueAsString(info));
		}
	}
	
	public void updateInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		token = request.getHeader("Authorization");
		String[] tokenArr = token.split(":");
		int userId = us.getUser(tokenArr[0]).getId();
		int infoId = is.getInfoByUserId(userId).getId();
		
		String infoJSON = request.getReader().readLine();
		ObjectMapper om = new ObjectMapper();
		Info info = om.readValue(infoJSON, Info.class);
		info.setUserId(userId);
		info.setId(infoId);
		
		boolean updated = is.updateInfo(info.getId(), info);
		
		if (updated) {
			response.setStatus(200);
		}
		else {
			response.setStatus(500);
		}
	}
	
}
