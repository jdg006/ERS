package com.revature.delegates;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.ers.dao.impl.UserDaoImpl;
import com.revature.ers.model.User;

public class AuthDelegate {
	
	private UserDaoImpl udi = new UserDaoImpl();
	
	public boolean isAuthorized(HttpServletRequest request) {
		String authToken = request.getHeader("Authorization");
		if(authToken!=null) {
			String[] tokenArr = authToken.split(":");
		
			if(tokenArr.length == 2) {
				String idStr = tokenArr[0];
				String userRoleStr = tokenArr[1];
				if(idStr.matches("^\\d+$") && userRoleStr.matches("^\\d+$")) {
					
					User u = udi.getUser(Integer.parseInt(idStr));
					if(u!=null && u.getPermissionLevel() == Integer.parseInt(userRoleStr)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		User u = udi.getUser(email);

		if(u!=null && u.getPassword().contentEquals(password)) {
			
			String token = u.getEmail()+":"+u.getPermissionLevel()+":"+u.hashCode();
			response.setStatus(200);
			response.setHeader("Authorization", token);
			
		} else {
			
			response.sendError(401);
		}
		
	}
}

