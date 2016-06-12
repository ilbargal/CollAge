package bl;
import common.DataBaseHandler;
import models.Users;

import javax.xml.crypto.Data;

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

    public Users getUser(String email) {
        return DataBaseHandler.getInstance().singleQueryById("findUserByEmail", "email", email);
    }

    public void saveUser(Users myUser) {
        DataBaseHandler.getInstance().Persist(myUser);
    }

    public void updateUser(Users myUser) {
        DataBaseHandler.getInstance().Merge(myUser);
    }
}
