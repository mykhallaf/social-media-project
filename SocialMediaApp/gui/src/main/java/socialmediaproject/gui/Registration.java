package socialmediaproject.gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;



public class Registration {
    // Database connection parameters
    private static final String URL = "jdbc:mysql://localhost:3306/social_media_app";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "sqlmohakhallaf101101@#";

    private final String email;
    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;

    public Registration(String email, String username, String password, String firstName, String lastName) {

        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean registerUser() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            // Prepare SQL query
            String sql = "INSERT INTO users (email, password, first_name, last_name,username) VALUES (?, ?, ?, ?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set parameters
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, firstName);
            statement.setString(4, lastName);
            statement.setString(5, username);
            // Execute query
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Registration successful!");
                // Create a User object with the registered user's information
                User newUser = new User(email, username, firstName, lastName);
                // You can perform further actions with this User object if needed
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

    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        // Regular expression pattern for validating email addresses
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        return email.matches(emailPattern);
    }




    // Password validation method
    public boolean isValidPassword(String password) {
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