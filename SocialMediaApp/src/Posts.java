import java.util.ArrayList;
import java.util.List;

public class Post {
    private String username;
    private String postDate;
    private int numLikes;
    private int numShares;
    private int numComments;
    private List<String> comments;

    // CONSTRUCTOR
    public Post(String username, String postDate) {
        this.username = username;
        this.postDate = postDate;
        this.numLikes = 0;
        this.numShares = 0;
        this.numComments = 0;
        this.comments = new ArrayList<>();
    }

    // GETTERS AND SETTERS
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void incrementLikes() {
        this.numLikes++;
    }

    public int getNumShares() {
        return numShares;
    }

    public void incrementShares() {
        this.numShares++;
    }

    public int getNumComments() {
        return numComments;
    }

    public void incrementComments() {
        this.numComments++;
    }

    public List<String> getComments() {
        return comments;
    }

    // METHOD TO ADD COMMENT
    public void addComment(String comment) {
        comments.add(comment);
    }

    // METHOD TO REMOVE COMMENT
    public void removeComment(int commentIndex) {
        if (commentIndex >= 0 && commentIndex < comments.size()) {
            comments.remove(commentIndex);
        }
    }

    // METHOD TO EDIT COMMENT
    public void editComment(int commentIndex, String newComment) {
        if (commentIndex >= 0 && commentIndex < comments.size()) {
            comments.set(commentIndex, newComment);
        }
    }

    // METHOD TO DISPLAY POST
    public void displayPost() {
        System.out.println("Username: " + username);
        System.out.println("Date: " + postDate);
        System.out.println("Likes: " + numLikes);
        System.out.println("Comments: " + numComments);
        System.out.println("Shares: " + numShares);
    }
}
