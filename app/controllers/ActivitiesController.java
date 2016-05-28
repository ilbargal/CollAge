package controllers;
import bl.EventBL;
import com.fasterxml.jackson.core.JsonProcessingException;

import common.Utils;
import models.Events;
import play.mvc.Controller;
import play.mvc.Result;

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

    public Result addActivity() {
        System.out.println(request().body().asJson().toString());
        return ok();
    }
}
