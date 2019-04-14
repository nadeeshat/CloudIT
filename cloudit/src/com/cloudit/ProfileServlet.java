package com.cloudit;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProfileServlet extends HttpServlet {
	
	String qrContent = null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		
		qrContent = (String) session.getAttribute("textContent");
		
		session.setAttribute("profileDetails", qrContent);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/profile.jsp");
		dispatcher.forward(request, response);
	}

}
