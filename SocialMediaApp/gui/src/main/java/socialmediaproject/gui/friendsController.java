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

    private int currentUserId; 

    @FXML
    private void initialize() {
        currentUserId = SessionManager.getCurrentUserID();
        displayFriends();
    }

    @FXML
    private void onAddFriend(ActionEvent event) {
        String username = searchBar.getText().trim();
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

    private int findUserIdByUsername(String username) {
        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String dbUsername = "root";
        String dbPassword = "sqlmohakhallaf101101@#";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "SELECT user_id FROM users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; 
    }

    private boolean addFriend(int userId, int friendId) {
        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String dbUsername = "root";
        String dbPassword = "sqlmohakhallaf101101@#";

        if (isFriend(userId, friendId)) {
            return false; 
        }

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "INSERT INTO friends (user_id, friend_id) VALUES (?, ?)";
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
        StringBuilder friendListText = new StringBuilder();

        for (String friend : friends) {
            friendListText.append(friend).append("\n");
        }

        friendList.setText(friendListText.toString());
    }

    private List<String> getFriends(int userId) {
        List<String> friends = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/social_media_app";
        String dbUsername = "root";
        String dbPassword = "sqlmohakhallaf101101@#";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "SELECT u.username FROM friends f JOIN users u ON f.friend_id = u.user_id WHERE f.user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                friends.add(resultSet.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return friends;
    }
}
