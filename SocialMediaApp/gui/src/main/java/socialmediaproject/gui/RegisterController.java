package socialmediaproject.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void signupButtonOnAction(ActionEvent event) {
        // Reset signupLabel text
        signupLabel.setText("");

        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        String repassword = repasswordField.getText();

        Registration registration = new Registration(email, username, password, firstName, lastName);

        if (!registration.isValidEmail(email)) {
            signupLabel.setText("Email format should be example@example.com");
        } else if (!registration.isValidPassword(password)) {
            signupLabel.setText("Password must contain at least one uppercase character, numbers, and special characters");
        } else if (!password.equals(repassword)) {
            signupLabel.setText("Passwords do not match");
        } else {
            // All validations passed, proceed with user registration
            registration.registerUser();
            // Optionally, you can also set a success message if registration is successful
            // signupLabel.setText("Registration successful!");
        }
    }
}
