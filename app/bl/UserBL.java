package bl;
import common.DataBaseHandler;
import models.Users;

public class UserBL {
    private static UserBL _instance;

    private UserBL() {
    }

    public static UserBL getInstance() {
        if (_instance == null) {
            synchronized (UserBL.class) {
                if (_instance == null) {
                    _instance = new UserBL();
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

    public boolean checkUserValidation (Users usr) {
        return false;
    }

    public void insertUser(Users myUser) {

        DataBaseHandler.getInstance().Persist(myUser);
    }

    public void updateUser(Users myUser) {
        // TODO: insert to DB
    }
}
