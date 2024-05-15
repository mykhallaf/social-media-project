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


    public addPostController(int currentUserID) {
        this.currentUserID = currentUserID;
    }

    public void createPost(int currentUserID, String content) {
        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String username = "root";
        String password = "sqlmohakhallaf101101@#"; 

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "INSERT INTO posts (user_id, content, post_date) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, currentUserID); // Set the user_id
            preparedStatement.setString(2, content);
            preparedStatement.setObject(3, LocalDate.now());
            preparedStatement.executeUpdate();
            connection.close();
            postLabel.setText("Post has been created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            postLabel.setText("Error occurred while creating the post!");
        }
    }


    @FXML
    public void createPostHandler(ActionEvent event) {
        String content = writeContent.getText();
        int currentUserID = SessionManager.getCurrentUserID();
        createPost(currentUserID, content);
    }


}
