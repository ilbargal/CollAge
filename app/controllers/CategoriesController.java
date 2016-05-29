package controllers;

import bl.CategoryBL;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import common.Utils;
import models.Categories;
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
}
