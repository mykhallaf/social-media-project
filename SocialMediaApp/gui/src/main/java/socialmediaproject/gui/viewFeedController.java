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
        posts = getAllPosts(); //store the list of posts from database in textarea of posts
        currentPostIndex = 0; //sets index of first post to 0 to display first post in from database
        if (!posts.isEmpty()) {//if the posts list is not empty, display current post
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
        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String username = "root";
        String password = "sqlmohakhallaf101101@#";

        String query = "UPDATE posts SET likes_count = likes_count - 1 WHERE post_id = ?";//decrease number of likes of this post

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, postId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void incrementLikesForPost(int postId) {//increase number of likes of this post
        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String username = "root";
        String password = "sqlmohakhallaf101101@#";

        String query = "UPDATE posts SET likes_count = likes_count + 1 WHERE post_id = ?";

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
            String sql = "SELECT * FROM user_likes WHERE user_id = ? AND post_id = ?";//checks to see whether this user liked this post or not
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

    private void addUserLike(int userId, int postId) {//adds a record to the user_likes table in the database, indicating that a particular user  has liked a specific post
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

    private void removeUserLike(int userId, int postId) {//removes the record from user_likes table
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
            // display the previous post in the list
            displayPost(posts.get(currentPostIndex));
        }
    }

    private void displayPost(Posts post) {
        String authorName = getAuthorName(post.getUserId()); //uses the method getAuthorName with parameters being the user id of user who created the post to get the username of this user
        System.out.println("Author: " + authorName); //for console for testing
        Image profileImage = getProfileImage(post.getUserId());//get the profile picture of the post's author
        int likeCount = getLikeCount(post.getPostId());//get the amount of likes of this particular post

        author.setText(authorName);//puts in the author label the username of the post's creator
        this.post.setText(post.getContent());//put in the post textarea the retrieved post content from database
        profilePic.setImage(profileImage);//puts the users profile pic in the profile pic imageview
        counter.setText(String.valueOf(likeCount));//puts in the label of counter the number of likes of this specific post

        if (isPostLikedByUser(currentUserId, post.getPostId())) {
            likeButton.setText("Unlike");
        } else {
            likeButton.setText("Like");
        }


        List<String> postComments = getCommentsForPost(post.getPostId());// Fetch comments for the current post
        comments.setText(String.join("\n", postComments)); // Set the comments text area with all comments separated by newline

    }

    private String getAuthorName(int userId) {//get username of post creator
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

    private Image getProfileImage(int userId) {//get profile picture of post creator
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

    public List<Posts> getAllPosts() {//retrieves all data of this post from the database
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

    private int getLikeCount(int postId) {//get number of likes of this specific post
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

    private List<String> getCommentsForPost(int postId) {//store all comments for this specific post in an array list
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
        // Retrieve the content of the comment from the text input field
        String commentContent = writeComment.getText();

        // Check law el comment content isn't empty
        if (!commentContent.isEmpty()) {
            // Retrieve the current post being displayed
            Posts currentPost = posts.get(currentPostIndex);

            // Add the comment to the current post
            addCommentToPost(currentUserId, currentPost.getPostId(), commentContent);

            // retrieve the username of the current user
            String currentUsername = getAuthorName(currentUserId);

            // benetba3 the comment content along with the username to the comments area
            comments.appendText(currentUsername + ": " + commentContent + "\n");

            // refresh the display to show the updated post with the new comment
            displayPost(currentPost);

            // bensheel ay comment kan maktoob men el text field
            writeComment.clear();
        }
    }


    private void addCommentToPost(int userId, int postId, String content) {//adds comment to this specific post in comments table
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

    public List<Posts> getAllPostsSortedByLikes() {//sort posts in a descending order from most likes to least likes
        List<Posts> posts = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String username = "root";
        String password = "sqlmohakhallaf101101@#";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT post_id, user_id, content, likes_count FROM posts ORDER BY likes_count DESC";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {//iterates through database resultset and extracts the post info
                int postId = resultSet.getInt("post_id");
                int userId = resultSet.getInt("user_id");
                String content = resultSet.getString("content");
                int likesCount = resultSet.getInt("likes_count");

                Posts post = new Posts(postId, userId, content, likesCount);//creats a new Posts object
                posts.add(post);//adds it to arraylist
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }

}
