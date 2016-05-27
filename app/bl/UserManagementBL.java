package bl;
import models.Users;
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

    public Users getUser(String email, String password) {
        // TODO: get user from DB
        return null;
    }

    public boolean isUserExistsWithEmail(String email) {
        // TODO: return it
        return false;
    }

    public void insertUser(Users myUser) {
        // TODO: insert to DB
    }

    public void updateUser(Users myUser) {
        // TODO: insert to DB
    }
}
