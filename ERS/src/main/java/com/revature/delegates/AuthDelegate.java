package com.revature.delegates;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.revature.ers.dao.impl.UserDaoImpl;
import com.revature.ers.model.User;

public class AuthDelegate {
	
	private UserDaoImpl udi = new UserDaoImpl();
	private static Logger log = Logger.getRootLogger();
	
	public boolean isAuthorized(HttpServletRequest request) {
		log.info("authorizing");
		String authToken = request.getHeader("Authorization");
		log.info(authToken);
		if(authToken!=null) {
			String[] tokenArr = authToken.split(":");
		
			if(tokenArr.length == 3) {
				String emailStr = tokenArr[0];
				String userRoleStr = tokenArr[1];
				String hashcode = tokenArr[2];
					
					User u = udi.getUser(emailStr);
					if(u!=null && u.getPermissionLevel() == Integer.parseInt(userRoleStr) && u.hashCode() == Integer.parseInt(hashcode)) {
						log.info("Authorized");
						return true;
					}
			}
		}
		log.info("failed authorization");
		return false;
	}
	
	public void authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("authenticating");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		User u = udi.getUser(email);

		if(u!=null && u.getPassword().contentEquals(password) && u.isApproved() == true) {
			
			String token = u.getEmail()+":"+u.getPermissionLevel()+":"+u.hashCode();
			response.setStatus(200);
			response.setHeader("Authorization", token);
			
		} else {
			
			response.sendError(401);
		}
		
	}
}

