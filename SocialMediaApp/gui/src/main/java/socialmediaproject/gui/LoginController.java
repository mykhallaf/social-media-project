package socialmediaproject.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;



public class LoginController implements Initializable{
    @FXML
    private Button loginButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button signupButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private ImageView lockImageView;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File brandingFile = new File("Images/Asu connect.jpg");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);

        File lockFile = new File("Images/lock.png");
        Image lockImage = new Image(lockFile.toURI().toString());
        lockImageView.setImage(lockImage);
    }


    public void loginButtonOnAction(ActionEvent event) {
        String email = emailTextField.getText();
        String password = passwordField.getText();

        Login login = new Login(email, password);
        if (login.authenticateUser()) {
            User currentUser = fetchUserInformationFromDatabase(email);
            SessionManager.setCurrentUser(currentUser);
            openDashboard();
        } else {
            loginMessageLabel.setText("Invalid input. Please try again.");
        }
    }

    public void signupButtonOnAction(ActionEvent event) {
       createAccountForm();
    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


    public void createAccountForm() {
        try {
            Stage stage = new Stage(); // Instantiate a new Stage object
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Register.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 600, 600);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void openDashboard() {
        try {
            Stage stage = new Stage(); // Instantiate a new Stage object
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 635, 437);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User fetchUserInformationFromDatabase(String email) {
        User user = null;
        String URL = "jdbc:mysql://localhost:3306/social_media_app";
        String USERNAME = "root";
        String PASSWORD = "sqlmohakhallaf101101@#";


        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            // Prepare SQL query to retrieve user information based on email
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);

            // Execute query and retrieve result set
            ResultSet resultSet = statement.executeQuery();

            // Check if the result set has a row
            if (resultSet.next()) {
                // Extract user information from the result set
                String username = resultSet.getString("username");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                // Create a User object with the retrieved information
                user = new User(email, username, firstName, lastName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
        }

        return user;
    }




}
