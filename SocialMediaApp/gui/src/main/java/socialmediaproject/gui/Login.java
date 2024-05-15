package socialmediaproject.gui;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;


public class Login {
    private String email;
    private String password;

    Login(String email, String password){
        this.email = email;
        this.password = password;
    }

    private String getEmail(){
        return email;
    }

    private String getPassword(){
        return password;
    }

    private void setEmail(String email) {
        Objects.requireNonNull(email, "Email cannot be null");

        // Validate email format
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        this.email = email;
    }

    private void setPassword(String password) {
        Objects.requireNonNull(password, "Password cannot be null");
        // Validate password (optional)
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Password have a mix of uppercase and lowercase characters, numbers, and special characters");
        }
        this.password = password;
    }

    public boolean checkPassword(String inputPassword){
        return this.password.equals(inputPassword);
    }


    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }


        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";//the regular email validation expression

        return email.matches(emailPattern);
    }


    private boolean isValidPassword(String password) {// Check if the password meets the specified criteria

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


        return hasUppercase && hasDigit && hasSpecialChar;
    }
    public boolean authenticateUser() {

        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String username = "root";
        String dbPassword = "sqlmohakhallaf101101@#";


        try (Connection connection = DriverManager.getConnection(url, username, dbPassword)) {

            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                statement.setString(2, password);
                try (ResultSet resultSet = statement.executeQuery()) {

                    return resultSet.next();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }



}

