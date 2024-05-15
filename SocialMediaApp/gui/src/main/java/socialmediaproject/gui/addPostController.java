package socialmediaproject.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class addPostController {
    @FXML
    private TextArea writeContent;
    @FXML
    private Button uploadPost;
    @FXML
    private Label postLabel;
    private int currentUserID;

    public addPostController() {

    }

    public void createPost(int currentUserID, String content) { // create a post and save it in our database
        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String username = "root";
        String password = "sqlmohakhallaf101101@#";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "INSERT INTO posts (user_id, content, post_date) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, currentUserID); // Set the user_id
            preparedStatement.setString(2, content); // set the post content
            preparedStatement.setObject(3, LocalDate.now()); // current time
            preparedStatement.executeUpdate();
            connection.close();
            postLabel.setText("Post has been created successfully!"); //message that shows that the post is created
        } catch (SQLException e) {
            e.printStackTrace();
            postLabel.setText("Error occurred while creating the post!"); //an error occurred while creating the post
        }
    }



    @FXML
    public void createPostHandler(ActionEvent event) { //when button is pressed, post is created
        String content = writeContent.getText();  // Get the content of the post from the TextArea
        int currentUserID = SessionManager.getCurrentUserID(); // Get the current user ID from the session manager
        createPost(currentUserID, content); // Create the post using the current user's ID
    }





}
