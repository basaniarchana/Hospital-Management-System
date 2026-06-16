package com.learnJDBC;

import java.sql.*;
import java.util.Scanner;

// Patient class handles all operations related to patients in the hospital management system
public class Patient {
    private Connection connection; // JDBC connection to the database
    private Scanner scanner;       // Scanner to take user input

    // Constructor to initialize the connection and scanner
    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    // Method to add a new patient to the database
    public void addPatient() {
        // Collect patient information from user
        System.out.println("Enter Patient's Name");
        String name = scanner.next();

        System.out.println("Enter Patient's Age");
        int age = scanner.nextInt();

        System.out.println("Enter Patient's Gender");
        String gender = scanner.next();

        try {
            // SQL query to insert new patient record
            String Query = "INSERT INTO patients(name, age, gender) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(Query);

            // Set values for the query placeholders
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);

            // Execute the insert query
            int affectedRows = preparedStatement.executeUpdate();

            // Check if patient was added successfully
            if (affectedRows > 0) {
                System.out.println("Patient added successfully.");
            } else {
                System.out.println("Failed to add patient!");
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Print SQL error if occurs
        }
    }

    // Method to view all patients from the database
    public void viewPatients() {
        String query = "SELECT * FROM patients"; // SQL query to fetch all patient records

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultset = preparedStatement.executeQuery(); // Execute the query

            // Display table headers
            System.out.println("Patients: ");
            System.out.println("+------------+--------------+-----+--------+");
            System.out.println("| Patient ID | Name         | Age | Gender |");
            System.out.println("+------------+--------------+-----+--------+");

            // Loop through the result set and display each patient's details
            while (resultset.next()) {
                int id = resultset.getInt("id");
                String name = resultset.getString("name");
                int age = resultset.getInt("age");
                String gender = resultset.getString("gender");

                // Print each row
                System.out.printf("|%-12s|%-14s|%-5s|%-10s|\n", id, name, age, gender);
                System.out.println("+------------+--------------+-----+----------+");
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exception
        }
    }

    // Method to check whether a patient exists in the database by ID
    public boolean getPatientByID(int id) {
        String query = "SELECT * FROM patients WHERE ID = ?"; // SQL query with condition

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id); // Set patient ID as parameter

            ResultSet resultset = preparedStatement.executeQuery(); // Execute the query

            // If a matching patient exists, return true
            if (resultset.next()) {
                return true;
            } else {
                return false; // No such patient found
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Print any errors during SQL execution
        }

        return false; // Return false in case of exception or no match
    }
}
