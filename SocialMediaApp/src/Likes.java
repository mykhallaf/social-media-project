public class Likes {
    private int numOfLikes;

    public Likes(int numOfLikes) {
        this.numOfLikes = numOfLikes;
        System.out.println(numOfLikes);
    }

    public void addLikes() {
        numOfLikes++;
    }

    public void removeLikes() {
        if (numOfLikes > 0) {
            numOfLikes--;
        }
    }

    public int getNumLikes() {
        return numOfLikes;
    }

    public void setNumLikes(int numOfLikes) {
        this.numOfLikes = numOfLikes;
    }
}
