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
        Integer id = 0;
        try {
            Form<Events> addActivityForm = formFactory.form(Events.class).bindFromRequest();
            Events newEvent = addActivityForm.get();
            EventBL.getInstance().addEvent(newEvent);
            id = newEvent.getId();
        }
        catch (Exception e) {
            return internalServerError(e.toString());
        }
        return ok(id.toString());
    }
}
