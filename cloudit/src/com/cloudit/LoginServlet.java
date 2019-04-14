package com.cloudit;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
	      
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String username = request.getParameter("name");
		String imageUrl = request.getParameter("imageUrl");
		String email = request.getParameter("email");
		String idToken = request.getParameter("id_token");
		
		try {
//			GoogleIdToken.Payload payload = IdTokenVerifierAndParser.getPayLoad(idToken);
			
//			String id = (String)payload.get("id");
//			String name = (String)payload.get("name");
//			String imageUrl = (String)payload.get("imageUrl");
//			String email = payload.getEmail();
			
			System.out.println("ID :"+id);
			System.out.println("User Name :"+username);
			System.out.println("Image URL :"+imageUrl);
			System.out.println("User Email :"+email);
			System.out.println("ID Token :"+idToken);
			
			AddDataModel adm = new AddDataModel();
			ArrayList<User> ulist = adm.addData(id, username, imageUrl, email, idToken);
			
			HttpSession session = request.getSession(true);
			
			if(ulist.size() != 1) //Check the number of User objects returned
			{
				session.setAttribute("id", "N/A");
				session.setAttribute("userName", "N/A");
				//session.setAttribute("qrcode", "N/A");
				session.setAttribute("email", "N/A");
			}
			else //Ideal scenario
			{
				session.setAttribute("id", ulist.get(0).getId());
				session.setAttribute("userName", ulist.get(0).getName());
				//session.setAttribute("qrcode", ulist.get(0).getQrCode());
				session.setAttribute("email", ulist.get(0).getEmail());
			}
			
			response.setHeader("Cache-Control","no-cache"); 
		    response.setHeader("Cache-Control","no-store"); 
		    response.setDateHeader("Expires", 0); 
		    response.setHeader("Pragma","no-cache");
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/views/welcome.jsp");
			dispatcher.forward(request, response);
			
		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
		
	}

}
