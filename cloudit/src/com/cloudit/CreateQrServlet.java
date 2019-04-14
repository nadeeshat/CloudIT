package com.cloudit;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateQrServlet extends HttpServlet {
	
	BufferedImage bi = null;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			String userId = request.getParameter("userid");
			String userName = request.getParameter("username");
			String userEmail = request.getParameter("useremail");
			String userAdd = request.getParameter("useradd");
			
			CreateQrModel qrmodel = new CreateQrModel(userId, userName, userEmail, userAdd);
		
			qrmodel.createQrFile();
						
			HttpSession session = request.getSession(false);
			
			session.setAttribute("id", userId);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/views/welcome.jsp");
			dispatcher.forward(request, response);
			
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
