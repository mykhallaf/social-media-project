import java.util.ArrayList;
import java.util.List;

public class Comments {
    private String name;
   private List<Integer> numofLikesList;
    private int replies;
    private String date;
    private List<String> commentList;

    public Comments(String name, List<Integer> numofLikesList, int replies, String date) {
        this.name = name;
        this.numofLikes = new ArrayList<>(numofLikesList);
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

    public List<Integer> getNumofLikesList() {
        return numofLikesList;
    }

    public void setNumofLikesList(List<Integer> numofLikesList) {
        this.numofLikesList = new ArrayList<>(numofLikesList);
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

    public void addLike(int likeCount) {
        numofLikesList.add(likeCount);
    }

    public void removeLike(int likeCount) {
        if (likeCount >= 0) {
            numofLikesList.remove(likeCount);
        }
    }
}
