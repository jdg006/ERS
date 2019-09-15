package com.revature.delegates;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewDelegate {
	
	public void returnView(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String uri = request.getServletPath();
		switch (uri) {
		case "/home":
			request.getRequestDispatcher("/static/views/home.html").forward(request, response);
			break;
		case "/users":
			request.getRequestDispatcher("/static/views/users.html").forward(request, response);
			break;
		case "/employee_home":
			request.getRequestDispatcher("/static/views/employee_home.html").forward(request, response);
			break;
		case "/manager_home":
			request.getRequestDispatcher("/static/views/manager_home.html").forward(request, response);
			break;
		case "/administrator_home":
			request.getRequestDispatcher("/static/views/administrator_home.html").forward(request, response);
			break;
		case "/new_account":
			request.getRequestDispatcher("/static/views/new_account.html").forward(request, response);
			break;
		case "/all_employees":
			request.getRequestDispatcher("/static/views/all_employees.html").forward(request, response);
			break;
		case "/help":
			request.getRequestDispatcher("/static/views/help.html").forward(request, response);
			break;
		default:
			response.sendError(404, "Static Resource Not Found");
		}
	}
}