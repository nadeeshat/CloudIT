package com.cloudit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

class AddDataModel {
	
	DbUtil util = null;
	ArrayList<User> userList = new ArrayList<User>(); // Create an ArrayList<User> object to store user/s returned from the database - Ideally list should contain only one user 
	
	public AddDataModel() throws ClassNotFoundException, SQLException
	{
		util = new DbUtil();
	}
	
	public ArrayList<User> addData(String id, String username, String imgUrl, String email, String idToken)
	{
		int rowCount = 0;
		int updatedRows = 0;
		
		try {
			
			util.getConnection();
			
			/* Check whether user already exist in the database */
			String sql1 = "SELECT Count(*) FROM tbluserdata WHERE id='"+id+"';";
			String sql2 = null;
			
			ResultSet myRs = util.selectQuery(sql1);
			
			while(myRs.next())
			{
				rowCount = myRs.getInt(1);
			}
			
			if(rowCount == 0) //If user does not exist in the database
			{
				/* Inserting user details to the database */
				sql2 = "INSERT INTO tbluserdata VALUES('"+id+"', '"+username+"', '"+imgUrl+"', '"+email+"', '"+idToken+"', 'N/A');";
				updatedRows = util.updateQuery(sql2);
				
				if(updatedRows>0)
				{
					System.out.println("Successfully inserted user details!");
				}
			}
			else //If the user already exist in the database
			{
				/* Updating the ID Token for the particular user */
				sql2 = "UPDATE tbluserdata SET idtoken='"+idToken+"' WHERE id="+id+";";
				updatedRows = util.updateQuery(sql2);
				
				if(updatedRows>0)
				{
					System.out.println("Successfully updated user details!");
				}
			}
			
			showData(id); // Call a method to get user details from the database
			
			return userList;
			
		} catch (ClassNotFoundException e) {
			
			System.out.println(e.getMessage());
			
			return null;
			
		} catch (SQLException e) {
			
			System.out.println(e.getMessage());
			
			return null;
			
		}
	}
	
	/* Private method that retrieve user details from the database */
	private void showData(String id) throws SQLException
	{
		String sql3 = "SELECT * FROM tbluserdata WHERE id="+id+";";
		
		ResultSet myRs2 = util.selectQuery(sql3);
		
		while(myRs2.next())
		{
			String uId = myRs2.getString("id");
			String uName = myRs2.getString("name");
			String uImgUrl = myRs2.getString("imageurl");
			String uEmail = myRs2.getString("email");
			String uIdToken = myRs2.getString("idtoken");
			String uQrCode = myRs2.getString("qrcode");
			
			User loggedUser = new User(uId, uName, uImgUrl, uEmail, uIdToken, uQrCode);
			
			userList.add(loggedUser);
		}
	}
}


