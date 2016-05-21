package bl;

import models.User;

public class UserManagementBL {
    private static UserManagementBL _instance;

    private UserManagementBL() {
    }

    public static UserManagementBL getInstance() {
        if (_instance == null) {
            synchronized (UserManagementBL.class) {
                if (_instance == null) {
                    _instance = new UserManagementBL();
                }
            }
        }
        return _instance;
    }

    public User getUser(String email, String password) {
        // TODO: get user from DB
        return null;
    }

    public boolean isUserExistsWithEmail(String email) {
        // TODO: return it
        return false;
    }

    public void insertUser(User myUser) {
        // TODO: insert to DB
    }

    public void updateUser(User myUser) {
        // TODO: insert to DB
    }
}
