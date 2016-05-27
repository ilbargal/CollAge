package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import common.DataBaseHandler;
import common.Utils;
import models.Users;
import play.mvc.Controller;
import play.mvc.Result;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class MainController  extends Controller {

    public Result login(String email, String password) throws JsonProcessingException {
        //User currUser = UserManagementBL.getInstance().getUser(email, password);

        //if (Objects.nonNull(currUser))
        //    return ok(Utils.convertObjectToJsonString(currUser));
        //else return internalServerError("No user exists");
        return ok();
    }

    public Result register(String email, String password) throws JsonProcessingException {
//        User currUser = new User();
//        currUser.setId(email);
//        currUser.setPassword(password);
//
//        UserManagementBL.getInstance().insertUser(currUser);
//        return ok(Utils.convertObjectToJsonString(currUser));
        return ok();
    }

    public Result getUsers () throws JsonProcessingException {
        List<Users> users = DataBaseHandler.getInstance().query("findAllUsers");
        System.out.println(Utils.convertObjectToJsonString(users));
        return ok();
    }
}
