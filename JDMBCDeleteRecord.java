package com.learnJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDMBCDeleteRecord {

    public static void main(String[] args) {

        Connection con = null;
        Statement stmt = null;

        String url = "jdbc:mysql://localhost:3306/JDBCMySQL";
        String username = "root";
        String password = "Archu_123";

        String DeleteRecord = "delete from students where id = 2";

        try {
           
            Class.forName("com.mysql.jdbc.driver");
        } catch (ClassNotFoundException e) {
            System.out.println("In catch.mysql.jdbc.driver");
        } 

        try {
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            stmt.execute(DeleteRecord);
            System.out.println("Successfully Deleted");
        } catch (SQLException e) {
            System.out.println("In catch" + e.getMessage());
        } finally {
            if (con != null) {	
                try {
                    stmt.close();
                    con.close();
                    System.out.println("Terminated Successfully");
                } catch (Exception e) {
                    System.out.println("Oops ! some serious issue");
                }
            }
        }
    }
}