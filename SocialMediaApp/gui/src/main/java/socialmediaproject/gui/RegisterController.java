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

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController {
    @FXML
    private Button cancelButton;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField repasswordField;
    @FXML
    private Label signupLabel;

    public void cancelButtonOnAction(ActionEvent event) {//exits the window
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void signupButtonOnAction(ActionEvent event) {
        // Reset signupLabel text
        signupLabel.setText("");//initialise label as empty

        //these are the info user inputs when registering
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        String repassword = repasswordField.getText();

        Registration registration = new Registration(email, username, password, firstName, lastName);
        // an object of class registration is made with these parameters
        if (!registration.isValidEmail(email)) {
            signupLabel.setText("Email format should be example@example.com");
        } else if (!registration.isValidPassword(password)) {
            signupLabel.setText("Password must contain at least one uppercase character, numbers, and special characters");
        } else if (!password.equals(repassword)) {
            signupLabel.setText("Passwords do not match");
        } else {
            int userID = registration.registerUser(); // Change registerUser() to return the userID
            if (userID != -1) { // Check if registration was successful
                User currentUser = new User(email, username, firstName, lastName, userID); // Pass userID to User constructor
                SessionManager.setCurrentUser(currentUser);
                signupLabel.setText("User has been registered successfully!");
            } else {
                signupLabel.setText("Failed to register user. Please try again.");
            }
            // these check if each condition is met and if not, an error message is displayed accordingly
        }
    }



}
