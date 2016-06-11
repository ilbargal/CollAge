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

    public Result signup() throws JsonProcessingException {

        Form<Users> signUpForm = formFactory.form(Users.class).bindFromRequest();

        Users newUser = signUpForm.get();

        // Todo: remove this. i just pust it here to bypass the null constraint

        UserBL.getInstance().insertUser(newUser);


        return ok("Success");

//        try {
//            Users users = new Users();
//            JsonNode body = request().body().asJson().get("currUser");
//
//            return ok(Utils.convertObjectToJsonString(users));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return internalServerError(e.getStackTrace()[0].toString());
//        }
    }

    public Result saveUser() {
        try {
            Form<Users> signUpForm = formFactory.form(Users.class).bindFromRequest();
            Users savedUser = signUpForm.get();
            savedUser.setPassword("1234");
            UserBL.getInstance().saveUser(savedUser);

            // TODO: password always change to 1234 -
        }
        catch (Exception e) {
            return internalServerError(e.toString());
        }

        return ok("Success");
    }

    public Result joinActivity(String userId, String eventId){
        


        return ok("Success");
    }
}
