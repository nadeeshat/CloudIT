package com.cloudit;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ViewQrServlet extends HttpServlet {
	
	BufferedImage bi = null;
	String uid = null;
	String qrContent = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		try {
				
			HttpSession session = request.getSession(false);
			uid = (String) session.getAttribute("id");
			
			CreateQrModel qrmodel = new CreateQrModel(uid);
		
			bi = qrmodel.retrieveQrFile();
			qrContent = qrmodel.decodeQrCode(bi);
			
			session.setAttribute("textContent", qrContent);
			
			response.setContentType("image/png");
				
			OutputStream out = response.getOutputStream();
			ImageIO.write(bi, "png", out);
			out.close();
			
		}catch(Exception e)
		{
				System.out.println(e.getMessage());
		}
	}
}
