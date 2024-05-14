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
    private int currentUserId; // Assuming current user ID is available after user logs in

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
        posts = getAllPostsSortedByLikes();
        currentPostIndex = 0;
        if (!posts.isEmpty()) {
            displayPost(posts.get(currentPostIndex));
        }
    }


    @FXML
    public void likeOnAction(ActionEvent event) {
        Posts currentPost = posts.get(currentPostIndex);
        int postId = currentPost.getPostId();

        if (isPostLikedByUser(currentUserId, postId)) {
            decrementLikesForPost(postId);
            removeUserLike(currentUserId, postId);
            likeButton.setText("Like");
        } else {
            incrementLikesForPost(postId);
            addUserLike(currentUserId, postId);
            likeButton.setText("Unlike");
        }

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

    @FXML
    public void nextPostOnAction(ActionEvent event) {
        if (currentPostIndex < posts.size() - 1) {
            currentPostIndex++;
            displayPost(posts.get(currentPostIndex));
        }
    }

    @FXML
    public void previousPostOnAction(ActionEvent event) {
        if (currentPostIndex > 0) {
            currentPostIndex--;
            displayPost(posts.get(currentPostIndex));
        }
    }

    private void displayPost(Posts post) {
        String authorName = getAuthorName(post.getUserId());
        System.out.println("Author: " + authorName); // Debug statement
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
                System.out.println("User not found with user_id: " + userId); // Debug statement
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
                System.out.println("Fetched comment: " + authorName + ": " + content); // Debug statement
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

            // Retrieve the current user's username
            String currentUsername = getAuthorName(currentUserId);

            // Display the comment with the current user's username
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
