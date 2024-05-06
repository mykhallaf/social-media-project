
package com.mycompany.advancedproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Registration {
    private static final String URL = "jdbc:mysql://localhost:3306/social_media_app";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "sqlmohakhallaf101101@#";

    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public Registration(String email, String username, String password, String firstName, String lastName) {
        // Validate input data
        Objects.requireNonNull(email, "Email cannot be null");
        Objects.requireNonNull(username, "Username cannot be null");
        Objects.requireNonNull(password, "Password cannot be null");
        Objects.requireNonNull(firstName, "First name cannot be null");
        Objects.requireNonNull(lastName, "Last name cannot be null");

        // Validate email format
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        // Validate password
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Password must contain a mix of uppercase and lowercase characters, numbers, and special characters");
        }

        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean registerUser() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            // Prepare SQL query
            String sql = "INSERT INTO users (email, password, first_name, last_name) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set parameters
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, firstName);
            statement.setString(4, lastName);

            // Execute query
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Registration successful!");
                return true;
            } else {
                System.out.println("Registration failed!");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Email validation method using InternetAddress
    private boolean isValidEmail(String email) {
        try {
            InternetAddress emailAddress = new InternetAddress(email);
            // Ensure that the parsed email address has a valid format
            emailAddress.validate();
            return true;
        } catch (AddressException e) {
            // If an AddressException is thrown, the email address is invalid
            return false;
        }
    }

    // Password validation method
    private boolean isValidPassword(String password) {
        // Check if the password meets the specified criteria
        if (password.length() < 8) {
            return false; // Password length is less than 8 characters
        }

        boolean hasUppercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
        String specialCharacters = "!@#$%^&*()-_=+[{]}\\|;:'\",.<>?/";

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (specialCharacters.contains(String.valueOf(ch))) {
                hasSpecialChar = true;
            }
        }

        // Check if all required criteria are met
        return hasUppercase && hasDigit && hasSpecialChar;
    }

}
