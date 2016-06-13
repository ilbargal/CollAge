package controllers;
import bl.CategoryBL;
import bl.EventBL;
import bl.UserBL;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import common.DataBaseHandler;
import common.Utils;
import models.Categories;
import models.Events;
import models.Users;
import org.h2.engine.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.xml.crypto.Data;
import java.util.*;
import java.util.stream.Stream;

import machineLearning.*;

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

    public Result GetRecommendedActivities(String userMail){

        try {
            Users user = UserBL.getInstance().getUser(userMail);
            List<Events> allEvents = EventBL.getInstance().getAllEvents();

            machineLearning ml = machineLearning.getInstance();
            Collection<Events> events = ml.getRecommendedEventsByUser(user, allEvents);

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

    public Result saveEventStatus() {
        JsonNode detailsFromClient = request().body().asJson();
        Integer eventId = detailsFromClient.get("eventId").asInt();
        final String userEmail = detailsFromClient.get("userMail").asText();
        Integer status = detailsFromClient.get("statusCode").asInt();

        try {
            final Events currEvent = EventBL.getInstance().getEventById(eventId);

            Users[] allUsersofEvent = (Users[]) currEvent.getUsers().toArray(new Users[]{});
            Users currUser = Arrays.stream(allUsersofEvent)
                                        .filter(x -> x.getMail().equals(userEmail))
                                        .findFirst().orElse(null);
            if (currUser == null) {
                currUser = UserBL.getInstance().getUser(userEmail);
                currEvent.getUsers().add(currUser);
            }

            // Save DB
            EventBL.getInstance().saveEventWithStatus(currEvent, userEmail, status);

        }
        catch (Exception e) {
            return internalServerError(e.toString());
        }
        return ok();
    }
}
