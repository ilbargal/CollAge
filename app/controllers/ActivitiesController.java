package controllers;
import bl.CategoryBL;
import bl.EventBL;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.google.inject.Inject;
import common.Utils;
import models.Categories;
import models.Events;
import models.Users;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ActivitiesController extends Controller {

    public Result getAllActivities() throws JsonProcessingException {

        try {
            List<Events> events = EventBL.getInstance().getAllEvents();
            return ok(Utils.convertObjectToJsonString(events));
        } catch (Exception e) {
            e.printStackTrace();
            return internalServerError(e.getStackTrace()[0].toString());
        }
    }

    public Result getActivity(String id) throws JsonProcessingException {
        Events evt = EventBL.getInstance().getEventById(Integer.valueOf(id));

        return ok(Utils.convertObjectToJsonString(evt));
    }

    @Inject
    FormFactory formFactory;
    public Result addActivity() {
        try {
            Form<Events> addActivityForm = formFactory.form(Events.class).bindFromRequest();
            Events newEvent = addActivityForm.get();
            Collection<Categories> categories = new ArrayList<Categories>();
            categories.add(CategoryBL.getInstance().getCategoryById(1));
            categories.add(CategoryBL.getInstance().getCategoryById(2));
            newEvent.setCategories(categories);
            EventBL.getInstance().addEvent(newEvent);
        }
        catch (Exception e) {
            return internalServerError(e.toString());
        }
        return ok();
    }
}
