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

public class dashboardController implements Initializable{

    @FXML
    private BorderPane borderplane;
    @FXML
    private Button user;
    @FXML
    private Button createPost;
    @FXML
    private Button viewFeed;
    @FXML
    private Button friends;



    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

    @FXML
    private void btnUser(ActionEvent event) throws IOException { //when user is pressed, go to user scene
        AnchorPane view = FXMLLoader.load(getClass().getResource("user.fxml"));
        borderplane.setCenter(view);
    }

    @FXML
    private void btnCreatePost(ActionEvent event)throws IOException{ //when pressed, go to create post scene
        AnchorPane view = FXMLLoader.load(getClass().getResource("createPost.fxml"));
        borderplane.setCenter(view);
    }

    @FXML
    private void btnViewFeed(ActionEvent event)throws IOException{ //when pressed, go to view feed scene
        AnchorPane view = FXMLLoader.load(getClass().getResource("viewFeed.fxml"));
        borderplane.setCenter(view);
    }

    @FXML
    private void btnFriends(ActionEvent event)throws IOException{ //when pressed, go to friends scene
        AnchorPane view = FXMLLoader.load(getClass().getResource("friends.fxml"));
        borderplane.setCenter(view);
    }


}
