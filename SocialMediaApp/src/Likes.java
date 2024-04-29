/*deleted class*/
public class Likes {
    private int numOfLikes;

    public Likes() {
        this.numOfLikes = 0;
    }

    public int getLikes() {
        return numOfLikes;
    }

    public void setLikes(int numOfLikes) {
        this.numOfLikes = numOfLikes;
    }

    // Method to add a like
    public void addLike() {
        numOfLikes++;
    }

    // Method to remove a like (if likes are not 0)
    public void removeLike() {
        if (numOfLikes > 0) {
            numOfLikes--;
        }
    }
}
