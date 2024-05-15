package socialmediaproject.gui;

public class SessionManager {
    private static int currentUserID;
    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
        if (user != null) {
            currentUserID = user.getUserID();
        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUserID(int userID) {
        currentUserID = userID;
    }

    public static int getCurrentUserID() {
        return currentUserID;
    }
}
