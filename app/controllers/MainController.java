package controllers;

import bl.UserManagementBL;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.Utils;
import models.Event;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

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
}
