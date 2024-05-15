package socialmediaproject.gui;

import java.sql.*;
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

    public int registerUser() {
        int userID = -1;//there might be a possibility of not getting a valid result, so -1 is here for this reason

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            // Prepare SQL query
            String sql = "INSERT INTO users (email, password, first_name, last_name, username) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Set parameters
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, firstName);
            statement.setString(4, lastName);
            statement.setString(5, username);


            int rowsInserted = statement.executeUpdate();// executes the SQL insert query
            if (rowsInserted > 0) {
                System.out.println("Registration successful!");
                //resultset is a pointer for the current row
                ResultSet generatedKeys = statement.getGeneratedKeys();//receives auto-incremented primary keys
                if (generatedKeys.next()) {//if there are any generated keys
                    userID = generatedKeys.getInt(1); // Retrieve the auto-generated user ID
                }
            } else {
                System.out.println("Registration failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userID;
    }


    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }


        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";// Regular expression pattern for validating email addresses

        return email.matches(emailPattern);
    }





    public boolean isValidPassword(String password) {

        if (password.length() < 8) {
            return false;
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

}