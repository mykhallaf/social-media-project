package socialmediaproject.gui;

public class Posts {
    private int postId;
    private int userId;
    private String content;
    private int likesCount;

    public Posts(int postId, int userId, String content, int likesCount) {
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.likesCount = likesCount;
    }

    // Getters
    public int getPostId() {
        return postId;
    }

    public int getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    // Setters
    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }
}
