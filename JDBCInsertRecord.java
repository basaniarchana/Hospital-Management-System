package com.learnJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCInsertRecord {

	public static void main(String[] args) {
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs   = null;
		
		String URL = "jdbc:mysql://localhost:3306/JDBCMySQL";
		
		// USerName and Password while installing SQL
		String Username = "root";
		String Password = "Archu_123";
				
		try {
			
			Class.forName("com.mysql.jdbc.driver");
			
		}catch(ClassNotFoundException e)
		{
			
			System.out.println("In catch "+e.getMessage());
			
		}
		
try {
			
			con = DriverManager.getConnection(URL,Username,Password);
			
			stmt = con.createStatement();
			
			stmt.execute("insert into Students values (3,'Shalu','CSD')");
			stmt.execute("insert into Students values (4,'Saina Nehwal','ECE')");
			stmt.execute("insert into Students values (5,'Sania Mirza','EEE')");
			stmt.execute("insert into Students values (6,'Sindhu','Mech')");
			System.out.println("Sucessfully inserted");
			
			//rs.close();
			stmt.close();
			con.close();
			
			
		} catch(SQLException e)
		{
			
			System.out.println("In catch "+e.getMessage());
			
		}

		finally
		{
			if(con != null)
			{
				try {
					//rs.close();
					stmt.close();
					con.close();
					
					System.out.println("Terminated Successfully");
				}catch(Exception e)
				{
					System.out.println("Oops ! some serious issue");
				}
			}
		}


	}

}