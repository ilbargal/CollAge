package controllers;

import bl.CategoryBL;
import bl.UserBL;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import common.Utils;
import models.Categories;
import models.Users;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class CategoriesController extends Controller {
    public Result getAllCategories() throws JsonProcessingException {
        try {
            List<Categories> categories = CategoryBL.getInstance().getAllCategories();
            return ok(Utils.convertObjectToJsonString(categories));
        }
        catch (Exception e) {
            return internalServerError(e.toString());
        }
    }

    public Result getAllCategories(String content) throws JsonProcessingException {
        try {
            List<Categories> categories = CategoryBL.getInstance().getAllCategories(content);
            return ok(Utils.convertObjectToJsonString(categories));
        }
        catch (Exception e) {
            return internalServerError(e.toString());
        }
    }

    public Result getUserCategories(String email) {
        try {
            Users ownerUser = UserBL.getInstance().getUser(email);
            return ok(Utils.convertObjectToJsonString(ownerUser.getCategories()));
        }
        catch (Exception e) {
            return internalServerError(e.toString());
        }
    }

    @Inject
    FormFactory formFactory;
    public Result addCategory(String name) {
        String message = "Success";
        Integer id = 0;
        try {
            Form<Categories> addCategory = formFactory.form(Categories.class).bindFromRequest();
            Categories newCategory = addCategory.get();
            newCategory.setId(CategoryBL.getInstance().getNextId());
            newCategory.setName(name);
            CategoryBL.getInstance().addCategory(newCategory);
        }
        catch (Exception e) {
            return internalServerError(e.toString());
        }
        return ok(message.toString());
    }
}
