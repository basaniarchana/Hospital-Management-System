package com.learnJDBC;

import java.sql.*; 

public class CodeOpt {

    public static void main(String[] args) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        String URL = "jdbc:mysql://localhost:3306/JDBCMySQL";
        String Username = "root";
        String Password = "Archu_123";
        String query = "SELECT * FROM Students";

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            con = DriverManager.getConnection(URL, Username, Password);

            // Execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.println("__________________________");
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String dept = rs.getString("dept");

                System.out.println("Student ID: " + id);
                System.out.println("Student Name: " + name);
                System.out.println("Student Department: " + dept);
                System.out.println("__________________________");
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("In catch: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
                System.out.println("Terminated Successfully");
            } catch (SQLException e) {
                System.out.println("Oops! some serious issue");
            }
        }
    }
}
