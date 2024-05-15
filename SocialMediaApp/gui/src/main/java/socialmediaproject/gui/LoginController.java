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
    private int currentUserID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){//load these images when this scene opens
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
        //saves email and password entered by user
        Login login = new Login(email, password);
        if (login.authenticateUser()) {//if user is verified, get the user's info from the database and set the user as current user, then open dashboard
            User currentUser = fetchUserInformationFromDatabase(email);
            SessionManager.setCurrentUser(currentUser);
            openDashboard();
        } else {
            loginMessageLabel.setText("Invalid input. Please try again."); //error message if validation fails
        }
    }

    public void signupButtonOnAction(ActionEvent event) {
        createAccountForm();//open registration form
    }


    public void createAccountForm() {//open registration scene
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


    public void openDashboard() {//opens dashboard scene
        try {
            Stage stage = new Stage();
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
        User user = null;//no user info has been retrieved yet
        String URL = "jdbc:mysql://localhost:3306/social_media_app";
        String USERNAME = "root";
        String PASSWORD = "sqlmohakhallaf101101@#";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int userID = resultSet.getInt("user_id"); // Retrieve user_id from the result set
                String username = resultSet.getString("username");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                user = new User(email, username, firstName, lastName, userID);//create user object with info retrieved from database
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }




}
