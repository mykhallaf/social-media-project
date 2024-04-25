import java.util.ArrayList;

public class User {
    private String username;
    private String bio;
    private String profilePicUrl;
    private ArrayList<User> friendList;
    private ArrayList<String> interests;

    public User(String username, String bio, String profilePicUrl) {
        this.username = username;
        this.bio = bio;
        this.profilePicUrl = profilePicUrl;
        this.friendList = new ArrayList<>();
        this.interests = new ArrayList<>();
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public ArrayList<User> getFriendList() {
        return friendList;
    }

    public void setFriendList(ArrayList<User> friendList) {
        this.friendList = friendList;
    }

    public ArrayList<String> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<String> interests) {
        this.interests = interests;
    }

   
    public void addFriend(User friend) {
        friendList.add(friend);
    }

    
    public void removeFriend(User friend) {
        friendList.remove(friend);
    }
}
