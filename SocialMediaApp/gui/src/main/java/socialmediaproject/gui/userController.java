package socialmediaproject.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class userController implements Initializable{
    @FXML
    private Label welcomeLabel;


    @Override
    public void initialize(URL url, ResourceBundle rb){
        // Access the current user from the SessionManager
        User currentUser = SessionManager.getCurrentUser();

        // Check if the current user exists
        if (currentUser != null) {
            // Display the welcome message along with the user's first and last names
            welcomeLabel.setText("Welcome back, " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
        } else {
            welcomeLabel.setText("Welcome!");
        }
    }
}
