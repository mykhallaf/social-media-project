import java.util.ArrayList;
import java.util.List;

public class Comments {
    private String name;
    private int numofLikes;
    private int replies;
    private String date;
    private List<String> commentList;

    public Comments(String name, int numofLikes, int replies, String date) {
        this.name = name;
        this.numofLikes = numofLikes;
        this.replies = replies;
        this.date = date;
        this.commentList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumofLikes() {
        return numofLikes;
    }

    public void setNumofLikes(int numofLikes) {
        this.numofLikes = numofLikes;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void addComment(String commentText) {
        commentList.add(commentText);
    }

    public void removeComment(int commentIndex) {
        if (commentIndex >= 0 && commentIndex < commentList.size()) {
            commentList.remove(commentIndex);
        }
    }

    public void editComment(int commentIndex, String newCommentText) {
        if (commentIndex >= 0 && commentIndex < commentList.size()) {
            commentList.set(commentIndex, newCommentText);
        }
    }

    public void addLike() {
        numofLikes++;
    }

    public void removeLike() {
        if (numofLikes > 0) {
            numofLikes--;
        }
    }
}
