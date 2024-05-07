package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class addPostController {
    @FXML
    private Button createPost;
    @FXML
    private TextField writeContent;
    @FXML
    private Button uploadPost;
    private Posts posts;

    public addPostController() {
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
        posts.createPost(content);
        writeContent.clear();
        System.out.println("Post Uploaded!");
        writeContent.setVisible(false);

    }

}
