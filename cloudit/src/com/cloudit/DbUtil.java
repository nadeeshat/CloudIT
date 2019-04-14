package com.cloudit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class DbUtil {
	
	String dbUrl = null;
	String username = null;
	String password = null;
	
	Connection myConn = null;
	Statement myStmt = null;
	ResultSet myRs = null;
	
	public DbUtil()
	{
		/*Assign database credentials */
		dbUrl = "jdbc:mysql://localhost:3306/dbcloudit";
		username = "cloud";
		password = "tharindu";
	}
	
	public void getConnection() throws SQLException, ClassNotFoundException
	{
		/*Load the MySQL driver */
		Class.forName("com.mysql.jdbc.Driver");
		
		/*Get the MySQL Connection */
		myConn = DriverManager.getConnection(dbUrl, username, password);
		System.out.println("Connection successful");
		
		myStmt = myConn.createStatement();
	}
	
	public ResultSet selectQuery(String sql) throws SQLException
	{
		myRs = myStmt.executeQuery(sql);
		
		return myRs;
	}
	
	public int updateQuery(String sql) throws SQLException
	{
		int rows = myStmt.executeUpdate(sql);
		
		return rows;
	}
	
	public void closeConnection() throws SQLException 
	{
		myConn.close();
	}

}
