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
        User currentUser = SessionManager.getCurrentUser(); // Access the current user from the SessionManager
        if (currentUser != null) { // Check if the current user exists
            welcomeLabel.setText("Welcome back, " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
            bioTextArea.setText(loadBioFromDatabase(currentUser.getEmail())); // Display the welcome message along with the user's first and last names
            byte[] imageData = loadImageFromDatabase(currentUser.getEmail()); //loads the image from database and stores it as a byte array
            if (imageData != null) {//if successfully retrieved from database
                Image image = new Image(new ByteArrayInputStream(imageData));//pass to image constructor of JavaFX Image object the byte array representation of the image data
                profilePic.setImage(image);
            }
        } else {
            welcomeLabel.setText("Welcome!");
        }
    }

    public void changePicOnAction(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();// Open a file chooser dialog
        fileChooser.setTitle("Choose Profile Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")//this allows only image files
        );


        File selectedFile = fileChooser.showOpenDialog(new Stage());// Show the dialog


        if (selectedFile != null) {// Checks if a file was selected
            try {

                byte[] imageData = new byte[(int) selectedFile.length()];// Convert the selected file to a byte array
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                fileInputStream.read(imageData);
                fileInputStream.close();
                saveImageToDatabase(imageData); // Save the byte array to the database
                Image image = new Image(selectedFile.toURI().toString());// Load the selected image and set it to the profile picture
                profilePic.setImage(image);//set the pic as profile pic
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveImageToDatabase(byte[] imageData) {//saves image that user sets as profile pic into the database as a BLOB(binary large object), which is the sql equivalent of byte array

        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String username = "root";
        String password = "sqlmohakhallaf101101@#";

        try {

            Connection connection = DriverManager.getConnection(url, username, password);


            String sql = "UPDATE users SET profile_image = ? WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);


            preparedStatement.setBytes(1, imageData);
            preparedStatement.setString(2, SessionManager.getCurrentUser().getEmail());


            preparedStatement.executeUpdate();


            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private byte[] loadImageFromDatabase(String email) {//load the BLOB image from database

        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String username = "root";
        String password = "sqlmohakhallaf101101@#";

        try {

            Connection connection = DriverManager.getConnection(url, username, password);


            String sql = "SELECT profile_image FROM users WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);


            ResultSet resultSet = preparedStatement.executeQuery();


            if (resultSet.next()) {

                return resultSet.getBytes("profile_image");
            }


            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateBio(ActionEvent event) {

        String newBio = bioTextArea.getText();// Get the user's input from the bioTextArea
        updateUserBioInDatabase(newBio);// Update the user's bio in the database


        User currentUser = SessionManager.getCurrentUser();// Update the user object in the session
        if (currentUser != null) {
            currentUser.setBio(newBio);
        }
    }

    private void updateUserBioInDatabase(String newBio) {

        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String username = "root";
        String password = "sqlmohakhallaf101101@#";

        try {

            Connection connection = DriverManager.getConnection(url, username, password);


            String sql = "UPDATE users SET bio = ? WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);


            preparedStatement.setString(1, newBio);
            preparedStatement.setString(2, SessionManager.getCurrentUser().getEmail()); // Assuming you have a method to get the email of the current user


            preparedStatement.executeUpdate();


            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String loadBioFromDatabase(String email) {

        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String username = "root";
        String password = "sqlmohakhallaf101101@#";

        try {

            Connection connection = DriverManager.getConnection(url, username, password);


            String sql = "SELECT bio FROM users WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);


            ResultSet resultSet = preparedStatement.executeQuery();


            if (resultSet.next()) {// Check if the result set contains data

                return resultSet.getString("bio");// Retrieve the bio from the result set
            }


            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }



}


