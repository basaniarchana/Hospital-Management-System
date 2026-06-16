package com.learnJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCPreparedStatement {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Connection con = null;
        ResultSet rs = null;

        String URL = "jdbc:mysql://localhost:3306/JDBCMySQL";
        String Username = "root";
        String Password = "Archu_123";

        String Query = "select * from students where name = ?";

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded!");
        } catch (ClassNotFoundException e) {
            // This line will be printed if the driver is not loaded correctly
            System.out.println("Drivers not loaded");
            System.out.println("In catch1: " + e.getMessage());
        }

        try {
            // Establish connection
            con = DriverManager.getConnection(URL, Username, Password);
            PreparedStatement preparedStatement = con.prepareStatement(Query);

            // Set the name parameter in the query
            preparedStatement.setString(1, "Maan");

            // Execute the query
            rs = preparedStatement.executeQuery();

            // Process the result set
            while (rs.next()) {
                System.out.println("");

                int id = rs.getInt("id");
                String name = rs.getString("name");
                String dept = rs.getString("dept");

                System.out.println("Student ID: " + id);
                System.out.println("Student Name: " + name);
                System.out.println("Student Department: " + dept);

                System.out.println("");
            }

        } catch (Exception e) {
            System.out.println("In catch: " + e.getMessage());
        } finally {
            if (con != null) {  
                try {
                    // Your original closing code
                    //stmt.close();  // This should be 'preparedStatement.close();'
                	rs.close();
                    con.close();
                    System.out.println("Terminated Successfully");
                } catch (Exception e) {
                    System.out.println("Oops ! some serious issue");
                }
            }
        }
    }
}