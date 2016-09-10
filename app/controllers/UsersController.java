package controllers;

import bl.UserBL;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;
import com.google.inject.Inject;
import common.Utils;
import models.Users;
import org.h2.engine.User;
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

    public Result getUser(String mail) {
        try {
            Users user = UserBL.getInstance().getUser(mail);

            if (user != null) {
                user.setPassword("");
            }
            return ok(Utils.convertObjectToJsonString(user));
        }
        catch(Exception e) {
            return internalServerError(e.toString());
        }

    }

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
            updatedUser.setPassword(UserBL.getInstance().getUser(updatedUser.getMail()).getPassword());
            UserBL.getInstance().updateUser(updatedUser);
            return ok(Utils.convertObjectToJsonString(updatedUser));
        }
        catch (Exception e) {
            return internalServerError(e.toString());
        }
    }

    public Result insertNewUser(String email, String password) {
        try {
            Users user = new Users();
            user.setMail(email);
            user.setPassword(password);
            UserBL.getInstance().updateUser(user);
            return ok("ok");
        }
        catch (Exception e) {
            return internalServerError(e.toString());
        }
    }

    public Result joinActivity(String userId, String eventId){
        return ok("Success");
    }
}
