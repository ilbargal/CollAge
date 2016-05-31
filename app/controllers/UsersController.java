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

        UserBL.getInstance().insertUser(newUser);

        return ok("Success");
    }

    public Result signin() throws JsonProcessingException {

        Form<Users> signInForm = formFactory.form(Users.class).bindFromRequest();

        Users usr = signInForm.get();

        UserBL.getInstance().getUser(usr.getMail(), usr.getPassword());

        return ok("Success");
    }
}