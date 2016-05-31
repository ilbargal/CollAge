package bl;
import common.DataBaseHandler;
import models.Users;

import java.util.List;

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

    public Users getUser(String mail, String password) {

        Object[] obj = new Object[2];
        obj[0]  = mail;
        obj[1] = password;

        List<Users> usr = DataBaseHandler.getInstance().queryByParams("findUser", obj);

        return usr.get(0);
    }

    public boolean isUserExistsWithEmail(String email) {
        // TODO: return it
        return false;
    }

    public void insertUser(Users myUser) {

        DataBaseHandler.getInstance().Persist(myUser);
    }

    public void updateUser(Users myUser) {
        // TODO: insert to DB
    }
}
