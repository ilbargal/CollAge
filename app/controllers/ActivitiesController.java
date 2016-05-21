package controllers;
import bl.EventBL;
import com.fasterxml.jackson.core.JsonProcessingException;
import common.Utils;
import models.Event;
import play.mvc.Controller;
import play.mvc.Result;
import scala.util.parsing.json.JSON;


import java.util.Date;
import java.util.List;

public class ActivitiesController extends Controller {

    public Result getAllActivities() throws JsonProcessingException {

        try {
            List<Event> events = EventBL.getInstance().getAllEvents();
            String JsonValue = Utils.convertObjectToJsonString(events);
            return ok(JsonValue);
        } catch (Exception e) {
            e.printStackTrace();
            return internalServerError(e.toString());
        }
    }

    public Result getActivity(String id) throws JsonProcessingException {
        List<Event> events = EventBL.getInstance().getAllEvents();
        for (Event currEvent: events) {
            if (String.valueOf(currEvent.id).equals(id)) {
                return ok (Utils.convertObjectToJsonString(currEvent));
            }
        }

        return internalServerError();
    }
}
