package bl;
import common.DataBaseHandler;
import models.Users;

import javax.xml.crypto.Data;
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

    public Users getUser(String email) {
        return DataBaseHandler.getInstance().singleQueryById("findUserByEmail", "email", email);
    }

    public Users getUsersByName(String name) {
        return DataBaseHandler.getInstance().singleQueryById("findUsersByName", "name", name);
    }

    public List<Users> getAllUsers(){
        return DataBaseHandler.getInstance().query("findAllUsers");
    }

    public void saveUser(Users myUser) {
        DataBaseHandler.getInstance().Persist(myUser);
    }

    public void updateUser(Users myUser) {
        DataBaseHandler.getInstance().Merge(myUser);
    }
}
