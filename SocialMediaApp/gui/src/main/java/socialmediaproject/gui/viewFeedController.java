package socialmediaproject.gui;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class viewFeedController implements Initializable {
    @FXML
    private Button addComment;

    @FXML
    private Label author;

    @FXML
    private TextArea comments;

    @FXML
    private Label counter;

    @FXML
    private Button likeButton;

    @FXML
    private Button nextPost;

    @FXML
    private TextArea post;

    @FXML
    private Button previousPost;

    @FXML
    private ImageView profilePic;
    @FXML
    private Button sortByLikes;
    @FXML
    private TextField writeComment;

    private List<Posts> posts;
    private int currentPostIndex;
    private int currentUserId; 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentUserId = SessionManager.getCurrentUserID();
        posts = getAllPosts();
        currentPostIndex = 0;
        if (!posts.isEmpty()) {
            displayPost(posts.get(currentPostIndex));
        }
    }

   @FXML
public void sortByLikesOnAction(ActionEvent event) {
    // Retrieve all posts sorted by likes
    posts = getAllPostsSortedByLikes();
    
    // Reset the current post index to the first post
    currentPostIndex = 0;
    
    // Check if the list of posts is not empty
    if (!posts.isEmpty()) {
        // Display the first post in the sorted list
        displayPost(posts.get(currentPostIndex));
    }


   @FXML
public void likeOnAction(ActionEvent event) {
    // Retrieve the displayed post
    Posts currentPost = posts.get(currentPostIndex);
    
    //we get the id of the current post
    int postId = currentPost.getPostId();

    // check if the current user already liked el post
    if (isPostLikedByUser(currentUserId, postId)) {
        // law the post is already liked by the user, decrement the like count lel post
        decrementLikesForPost(postId);
        
        // Remove  like for the post
        removeUserLike(currentUserId, postId);
        
        // Update the like button text to indicate "Like"
        likeButton.setText("Like");
    } else {
        // we law ba2a the post is not liked by the user, increment the like count for the post
        incrementLikesForPost(postId);
        
        // Add the user's like for the post
        addUserLike(currentUserId, postId);
        
        // Update the like button text to indicate "Unlike"
        likeButton.setText("Unlike");
    }

    // Refresh the display to show the updated post with like count and el text beta3 el zorar
    displayPost(currentPost);
}


    private void decrementLikesForPost(int postId) {
        executeUpdate("UPDATE posts SET likes_count = likes_count - 1 WHERE post_id = ?", postId);
    }

    private void incrementLikesForPost(int postId) {
        executeUpdate("UPDATE posts SET likes_count = likes_count + 1 WHERE post_id = ?", postId);
    }

    private void executeUpdate(String query, int postId) {
        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String username = "root";
        String password = "sqlmohakhallaf101101@#";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, postId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isPostLikedByUser(int userId, int postId) {
        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String username = "root";
        String password = "sqlmohakhallaf101101@#";
        boolean isLiked = false;

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT * FROM user_likes WHERE user_id = ? AND post_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, postId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                isLiked = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isLiked;
    }

    private void addUserLike(int userId, int postId) {
        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String username = "root";
        String password = "sqlmohakhallaf101101@#";
        String sql = "INSERT INTO user_likes (user_id, post_id) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, postId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void removeUserLike(int userId, int postId) {
        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String username = "root";
        String password = "sqlmohakhallaf101101@#";
        String sql = "DELETE FROM user_likes WHERE user_id = ? AND post_id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, postId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // lama nedoos 3ala zorar next byegeeb el post el ba3do
    @FXML
    public void nextPostOnAction(ActionEvent event) {
        // check if there are more posts ba3d el post el wa2feen 3aleh 
        if (currentPostIndex < posts.size() - 1) {
            // benet7arak lel post el ba3do by incrementing the current post index 
            currentPostIndex++;
            // display the next post in the list
            displayPost(posts.get(currentPostIndex));
        }
    }

    // lama nedoos 3ala zorar previous byegeeb el post el ablo
    @FXML
    public void previousPostOnAction(ActionEvent event) {
        // we check if there are posts available before the current post.
        if (currentPostIndex > 0) {
            // benet7arak lel post el ablo by decrementing the current post index 
            currentPostIndex--;
            // display the pevious post in the list
            displayPost(posts.get(currentPostIndex));
        }
    }

    private void displayPost(Posts post) {
        String authorName = getAuthorName(post.getUserId());
        System.out.println("Author: " + authorName); 
        Image profileImage = getProfileImage(post.getUserId());
        int likeCount = getLikeCount(post.getPostId());

        author.setText(authorName);
        this.post.setText(post.getContent());
        profilePic.setImage(profileImage);
        counter.setText(String.valueOf(likeCount));

        if (isPostLikedByUser(currentUserId, post.getPostId())) {
            likeButton.setText("Unlike");
        } else {
            likeButton.setText("Like");
        }

        // Fetch and display comments
        List<String> postComments = getCommentsForPost(post.getPostId());
        comments.clear();
        for (String comment : postComments) {
            comments.appendText(comment + "\n");
        }
    }

    private String getAuthorName(int userId) {
        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String username = "root";
        String password = "sqlmohakhallaf101101@#";
        String authorName = "";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT username FROM users WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                authorName = resultSet.getString("username");
            } else {
                System.out.println("User not found with user_id: " + userId); 
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return authorName;
    }

    private Image getProfileImage(int userId) {
        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String username = "root";
        String password = "sqlmohakhallaf101101@#";
        Image profileImage = null;

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT profile_image FROM users WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                byte[] imageBytes = resultSet.getBytes("profile_image");
                if (imageBytes != null) {
                    profileImage = new Image(new ByteArrayInputStream(imageBytes));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (profileImage == null) {
            profileImage = new Image("default-profile.png");
        }

        return profileImage;
    }

    public List<Posts> getAllPosts() {
        List<Posts> posts = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String username = "root";
        String password = "sqlmohakhallaf101101@#";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT post_id, user_id, content, likes_count FROM posts";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int postId = resultSet.getInt("post_id");
                int userId = resultSet.getInt("user_id");
                String content = resultSet.getString("content");
                int likesCount = resultSet.getInt("likes_count");

                Posts post = new Posts(postId, userId, content, likesCount);
                posts.add(post);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }

    private int getLikeCount(int postId) {
        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String username = "root";
        String password = "sqlmohakhallaf101101@#";
        int likeCount = 0;

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT likes_count FROM posts WHERE post_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, postId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                likeCount = resultSet.getInt("likes_count");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return likeCount;
    }

    private List<String> getCommentsForPost(int postId) {
        List<String> comments = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String username = "root";
        String password = "sqlmohakhallaf101101@#";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT users.username, comments.content FROM comments " +
                    "JOIN users ON comments.user_id = users.user_id WHERE comments.post_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, postId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String authorName = resultSet.getString("username");
                String content = resultSet.getString("content");
                System.out.println("Fetched comment: " + authorName + ": " + content); 
                comments.add(authorName + ": " + content);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comments;
    }

    @FXML
    public void addCommentOnAction(ActionEvent event) {
        String commentContent = writeComment.getText().trim();
        if (!commentContent.isEmpty()) {
            Posts currentPost = posts.get(currentPostIndex);
            addCommentToPost(currentUserId, currentPost.getPostId(), commentContent);

            String currentUsername = getAuthorName(currentUserId);

            comments.appendText(currentUsername + ": " + commentContent + "\n");

            displayPost(currentPost);
            writeComment.clear();
        }
    }


    private void addCommentToPost(int userId, int postId, String content) {
        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String username = "root";
        String password = "sqlmohakhallaf101101@#";
        String sql = "INSERT INTO comments (user_id, post_id, content) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, postId);
            preparedStatement.setString(3, content);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Posts> getAllPostsSortedByLikes() {
        List<Posts> posts = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String username = "root";
        String password = "sqlmohakhallaf101101@#";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT post_id, user_id, content, likes_count FROM posts ORDER BY likes_count DESC";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int postId = resultSet.getInt("post_id");
                int userId = resultSet.getInt("user_id");
                String content = resultSet.getString("content");
                int likesCount = resultSet.getInt("likes_count");

                Posts post = new Posts(postId, userId, content, likesCount);
                posts.add(post);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }

}
