package com.learnJDBC;

import java.util.*;
import java.sql.*;

public class HospitalManagementSystem {

    // Database connection details
    private static String url = "jdbc:mysql://localhost:3306/hospital";
    private static String username = "root";
    private static String password = "Archu_123";

    public static void main(String[] args) {
        // Load JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // Print error if driver is not found
        }

        Scanner scanner = new Scanner(System.in);

        try {
            // Establish database connection
            Connection connection = DriverManager.getConnection(url, username, password);

            // Create Patient and Doctor objects
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection);

            // Infinite loop for menu-based system
            while (true) {
                // Display menu
                System.out.println("Hospital Management System");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Please Enter Your Choice");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        // Add a new patient
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        // View all patients
                        patient.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        // View all doctors
                        doctor.ViewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        // Book an appointment
                        bookAppointment(patient, doctor, connection, scanner);
                        System.out.println();
                        break;
                    case 5:
                        // Exit the program
                        System.out.println("THANK YOU! FOR USING HOSPITAL MANAGEMENT SYSTEM!");
                        return;
                    default:
                        System.out.println("Please Enter Valid Input");
                        break;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle connection-related exceptions
        }
    }

    // Method to book appointment
    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
        // Get input from user
        System.out.println("Please Enter Patient ID: ");
        int patientID = scanner.nextInt();

        System.out.println("Please Enter Doctor ID: ");
        int doctorID = scanner.nextInt();

        System.out.println("Please Enter Appointment Date (YYYY-MM-DD): ");
        String appointmentDate = scanner.next();

        // Check if patient and doctor exist
        if (patient.getPatientByID(patientID) && doctor.getDoctorByID(doctorID)) {

            // Check if doctor is available on the given date
            if (checkDoctorAvailability(doctorID, appointmentDate, connection)) {
                // Prepare insert query
                String appointmentQuery = "INSERT INTO appointments(patients_id, doctor_id, appointment_date) VALUES (?, ?, ?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1, patientID);
                    preparedStatement.setInt(2, doctorID);
                    preparedStatement.setString(3, appointmentDate);

                    // Execute update
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Appointment Booked");
                    } else {
                        System.out.println("Appointment could not be booked");
                    }

                } catch (SQLException e) {
                    e.printStackTrace(); // Print SQL error
                }
            } else {
                System.out.println("Doctor not available on this date!!");
            }

        } else {
            System.out.println("Either doctor or patient does not exist!");
        }
    }

    // Method to check if doctor is available for given date
    public static boolean checkDoctorAvailability(int doctorID, String appointmentDate, Connection connection) {
        // Query to count existing appointments for the doctor on a specific date
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorID);
            preparedStatement.setString(2, appointmentDate);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                // If no appointment exists, doctor is available
                return count == 0;
            }

        } catch (Exception e) {
            e.printStackTrace(); // Handle SQL error
        }

        // If error occurs or doctor already has appointment
        return false;
    }
}
