package socialmediaproject.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class friendsController {

    @FXML
    private Button addFriendButton;

    @FXML
    private TextArea friendList;

    @FXML
    private TextField searchBar;

    @FXML
    private Label stateLabel;

    private int currentUserId; // This should be set to the logged-in user's ID

    @FXML
    private void initialize() { //get the user_id of current user using session manager class, then display friends
        currentUserId = SessionManager.getCurrentUserID();
        displayFriends();
    }

    @FXML
    private void onAddFriend(ActionEvent event) {
        String username = searchBar.getText();
        if (!username.isEmpty()) {
            int friendId = findUserIdByUsername(username);
            if (friendId != -1) {
                if (addFriend(currentUserId, friendId)) {
                    stateLabel.setText("User has been added successfully.");
                    displayFriends();
                } else {
                    stateLabel.setText("User is already your friend.");
                }
            } else {
                stateLabel.setText("User not found.");
            }
        }
    }

    private int findUserIdByUsername(String username) { //search for a user to add to friend list
        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String dbUsername = "root";
        String dbPassword = "sqlmohakhallaf101101@#";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "SELECT user_id FROM users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {//hold the data retrieved from a database after executing a query
                return resultSet.getInt("user_id");// this line retrieves the value of the user_id column from the current row of the ResultSet
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // User not found
    }

    private boolean addFriend(int userId, int friendId) {
        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String dbUsername = "root";
        String dbPassword = "sqlmohakhallaf101101@#";

        if (isFriend(userId, friendId)) { // Check if the friendship already exists
            return false; // Already friends
        }

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "INSERT INTO friends (user_id, friend_id) VALUES (?, ?)"; //add users id and the friends id
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, friendId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean isFriend(int userId, int friendId) {
        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String dbUsername = "root";
        String dbPassword = "sqlmohakhallaf101101@#";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "SELECT * FROM friends WHERE user_id = ? AND friend_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, friendId);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void displayFriends() {
        List<String> friends = getFriends(currentUserId);
        String friendListText = String.join("\n", friends);//concatenate the list elements into a single string with each friend separated by a newline character
        friendList.setText(friendListText);//Sets the concatenated string as the text of friendList
    }


    private List<String> getFriends(int userId) {//fetch friends from database
        List<String> friends = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String dbUsername = "root";
        String dbPassword = "sqlmohakhallaf101101@#";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "SELECT u.username FROM friends f JOIN users u ON f.friend_id = u.user_id WHERE f.user_id = ?";// selects the usernames of friends for a given user ID by joining the friends table with the users table based on their IDs and filtering the results by the user ID parameter
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {//// Adds each username retrieved from the ResultSet to the friends list.
                friends.add(resultSet.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return friends;
    }
}
