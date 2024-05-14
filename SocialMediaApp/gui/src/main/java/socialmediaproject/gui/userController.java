package socialmediaproject.gui;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class userController implements Initializable {
    @FXML
    private Label welcomeLabel;
    @FXML
    private Button changePic;
    @FXML
    private ImageView profilePic;
    @FXML
    private TextArea bioTextArea;
    @FXML
    private Button updateBio;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Access the current user from the SessionManager
        User currentUser = SessionManager.getCurrentUser();

        // Check if the current user exists
        if (currentUser != null) {
            // Display the welcome message along with the user's first and last names
            welcomeLabel.setText("Welcome back, " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");


            bioTextArea.setText(loadBioFromDatabase(currentUser.getEmail()));
            byte[] imageData = loadImageFromDatabase(currentUser.getEmail());
            if (imageData != null) {
                Image image = new Image(new ByteArrayInputStream(imageData));
                profilePic.setImage(image);
            }
        } else {
            welcomeLabel.setText("Welcome!");
        }
    }

    public void changePicOnAction(ActionEvent event) {
        // Open a file chooser dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Picture");
        // Set filters to only allow image files
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        // Show the dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        // Check if a file was selected
        if (selectedFile != null) {
            try {
                // Convert the selected file to a byte array
                byte[] imageData = new byte[(int) selectedFile.length()];
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                fileInputStream.read(imageData);
                fileInputStream.close();

                // Save the byte array to the database
                saveImageToDatabase(imageData);

                // Load the selected image and set it to the profile picture ImageView
                Image image = new Image(selectedFile.toURI().toString());
                profilePic.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveImageToDatabase(byte[] imageData) {
        // Connection parameters
        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String username = "root";
        String password = "sqlmohakhallaf101101@#";

        try {
            // Establish the database connection
            Connection connection = DriverManager.getConnection(url, username, password);

            // Prepare the SQL statement
            String sql = "UPDATE users SET profile_image = ? WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set parameters
            preparedStatement.setBytes(1, imageData);
            preparedStatement.setString(2, SessionManager.getCurrentUser().getEmail()); // Assuming you have a method to get the email of the current user

            // Execute the update
            preparedStatement.executeUpdate();

            // Close the connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private byte[] loadImageFromDatabase(String email) {
        // Connection parameters
        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String username = "root";
        String password = "sqlmohakhallaf101101@#";

        try {
            // Establish the database connection
            Connection connection = DriverManager.getConnection(url, username, password);

            // Prepare the SQL statement
            String sql = "SELECT profile_image FROM users WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if the result set contains data
            if (resultSet.next()) {
                // Retrieve the image data from the result set
                return resultSet.getBytes("profile_image");
            }

            // Close the connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateBio(ActionEvent event) {
        // Get the user's input from the bioTextArea
        String newBio = bioTextArea.getText();

        // Update the user's bio in the database
        updateUserBioInDatabase(newBio);

        // Update the user object in the session
        User currentUser = SessionManager.getCurrentUser();
        if (currentUser != null) {
            currentUser.setBio(newBio);
        }
    }

    private void updateUserBioInDatabase(String newBio) {
        // Connection parameters
        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String username = "root";
        String password = "sqlmohakhallaf101101@#";

        try {
            // Establish the database connection
            Connection connection = DriverManager.getConnection(url, username, password);

            // Prepare the SQL statement
            String sql = "UPDATE users SET bio = ? WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set parameters
            preparedStatement.setString(1, newBio);
            preparedStatement.setString(2, SessionManager.getCurrentUser().getEmail()); // Assuming you have a method to get the email of the current user

            // Execute the update
            preparedStatement.executeUpdate();

            // Close the connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String loadBioFromDatabase(String email) {
        // Connection parameters
        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String username = "root";
        String password = "sqlmohakhallaf101101@#";

        try {
            // Establish the database connection
            Connection connection = DriverManager.getConnection(url, username, password);

            // Prepare the SQL statement
            String sql = "SELECT bio FROM users WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if the result set contains data
            if (resultSet.next()) {
                // Retrieve the bio from the result set
                return resultSet.getString("bio");
            }

            // Close the connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }



}


