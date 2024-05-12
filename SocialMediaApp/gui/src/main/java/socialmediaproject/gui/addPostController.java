package socialmediaproject.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
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


import java.time.LocalDate;

public class addPostController{
    @FXML
    private Button createPost;
    @FXML
    private TextField writeContent;
    @FXML
    private Button uploadPost;

    public Posts posts;

    public addPostController () {
        uploadPost.setVisible(false);
        writeContent.setVisible(false);
    }

    public void clickaddPostButton(ActionEvent event){
        uploadPost.setVisible(true);
        writeContent.setVisible(true);
        writeContent.requestFocus();
    }
    public void addPost(ActionEvent event){
        String content = writeContent.getText();
        posts.createPost(content);
        writeContent.clear();
        System.out.println("Post Uploaded!");
        writeContent.setVisible(false);
    }



}