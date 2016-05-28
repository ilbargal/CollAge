package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import common.Utils;
import models.Users;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.List;

/**
 * Created by Gal on 28-May-16.
 */
public class UsersController extends Controller {

    public Result addUser() throws JsonProcessingException {

        try {
            Users users = new Users();
            JsonNode body = request().body().asJson().get("currUser");

            return ok(Utils.convertObjectToJsonString(users));
        } catch (Exception e) {
            e.printStackTrace();
            return internalServerError(e.getStackTrace()[0].toString());
        }
    }
}
