package com.cloudit;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.zxing.WriterException;

public class ScanQrServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userId = request.getParameter("userid");
		String decodeVal = request.getParameter("decodeQr");
		
		CreateQrModel qrmodel = new CreateQrModel(userId);
		try {
			
			int insertedRows = qrmodel.createQrHash(decodeVal);
			
			HttpSession session = request.getSession(false);
			
			String message = "N/A";
			
			if(insertedRows > 0)
			{
				message = "QR Code has been scanned successfully and added to the database";
			}
			else
			{
				message = "Record adding failed !";
			}
			
			session.setAttribute("message", message);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/views/welcome.jsp");
			dispatcher.forward(request, response);
			
		} catch (ClassNotFoundException e) {
			
			System.out.println(e.getMessage());
			
		} catch (WriterException e) {
			
			System.out.println(e.getMessage());
			
		} catch (SQLException e) {
			
			System.out.println(e.getMessage());
		}
	}
	

}
