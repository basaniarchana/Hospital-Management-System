package com.learnJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Doctor class handles operations related to doctors in the hospital database
public class Doctor {
    private Connection connection;

    // Constructor to initialize the database connection
    public Doctor(Connection connection) {
        this.connection = connection;
    }

    // Method to view all doctors from the database
    public void ViewDoctors() {
        String query = "SELECT * FROM Doctors"; // SQL query to fetch all doctor records

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultset = preparedStatement.executeQuery(); // Execute query

            // Display table headers
            System.out.println("Doctors: ");
            System.out.println("+------------+--------------+----------------+");
            System.out.println("| Doctor  ID | Name         | Specilization  |");
            System.out.println("+------------+--------------+----------------+");

            // Loop through result set and print each doctor's details
            while (resultset.next()) {
                int id = resultset.getInt("id");
                String name = resultset.getString("name");
                String Specilization = resultset.getString("Specilization");

                // Format and print each doctor's data row
                System.out.printf("|%-12s|%-14s|%-16s|\n", id, name, Specilization);
                System.out.println("+------------+--------------+----------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print SQL error if any
        }
    }

    // Method to check if a doctor with the given ID exists in the database
    public boolean getDoctorByID(int id) {
        String query = "SELECT * FROM doctors WHERE ID = ?"; // SQL query with parameter

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id); // Set doctor ID parameter
            ResultSet resultset = preparedStatement.executeQuery(); // Execute query

            // If doctor record exists, return true
            if (resultset.next()) {
                return true;
            } else {
                return false; // No such doctor
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print error
        }

        return false; // Default return in case of exception
    }
}
