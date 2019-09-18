package com.revature.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.log4j.Logger;

import com.revature.delegates.AuthDelegate;

/**
 * Servlet implementation class FrontController
 */


public class FrontController extends DefaultServlet {
	
	private static Logger log = Logger.getRootLogger();
	
	private static final long serialVersionUID = 1L;
	
	private RequestHelper rh = new RequestHelper();
	
	private AuthDelegate ad = new AuthDelegate();
       
    public FrontController() {
    	
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String path = request.getRequestURI().substring(request.getContextPath().length());
		
			if(path.startsWith("/static/")) {
				
				super.doGet(request, response);	
			} else {
				
				rh.processGet(request, response);
				
			}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			rh.processPost(request, response);
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		rh.processPut(request, response);
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		rh.processDelete(request, response);
	}

}