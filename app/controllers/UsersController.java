package controllers;

import bl.UserBL;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;
import com.google.inject.Inject;
import common.Utils;
import models.Users;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.Timestamp;

/**
 * Created by Gal on 28-May-16.
 */
public class UsersController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result saveUser() {
        try {
            Form<Users> signUpForm = formFactory.form(Users.class).bindFromRequest();
            Users savedUser = signUpForm.get();
            UserBL.getInstance().saveUser(savedUser);
        }
        catch (Exception e) {
            return internalServerError(e.toString());
        }
        return ok("Success");
    }

    public Result updateUser() {
        try {
            Form<Users> updatedForm = formFactory.form(Users.class).bindFromRequest();
            Users updatedUser = updatedForm.get();
            UserBL.getInstance().updateUser(updatedUser);
            return ok(Utils.convertObjectToJsonString(updatedUser));
        }
        catch (Exception e) {
            return internalServerError(e.toString());
        }
    }

    public Result joinActivity(String userId, String eventId){
        return ok("Success");
    }
}
