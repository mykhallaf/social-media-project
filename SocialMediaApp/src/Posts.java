import java.util.ArrayList;
import java.util.List;

public class Post {
    private String username;
    private String postDate;
    private List<Integer> numLikesList;
    private int numShares;
    private int numComments;
    private List<String> comments;
    private List<String> imageUrls;
    private List<String> videoUrls;
    

    // CONSTRUCTOR
    public Post(String username, String postDate) {
        this.username = username;
        this.postDate = postDate;
        this.numLikesList = new ArrayList<>();
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

    public List<Integer> getNumLikesList() {
        return numLikesList;
    }

    public void setNumLikesList(List<Integer> numLikesList) {

        this.numLikesList = numLikesList;

    }
    public void addLike(int likeCount) {

        numLikesList.add(likeCount);
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


    }
}
