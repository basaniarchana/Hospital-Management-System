package com.learnJDBC;
import java.sql.*;
public class StatementJDBC {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JDBCMySQL","root","Archu_123");
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(" SELECT * FROM students ");
      
      while(rs.next()) 
      {
    	  System.out.println("__________________________");
    	  int id = rs.getInt("id");
    	  String name = rs.getString("name");
    	  String dept = rs.getString("dept");
    	
    	  
    	  System.out.println("Student ID :" +id);
    	  System.out.println("Student Name: " +name);
    	  System.out.println("Student Department :"+dept);
    	  
    	  System.out.println("__________________________");
      }
      rs.close();
      stmt.close();
      con.close();
	}

}
