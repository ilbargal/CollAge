package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Event;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class MainController  extends Controller {
    ObjectMapper mapper = new ObjectMapper();
    ArrayList<Event> events = new ArrayList<Event>();

    public Result getAllEvents() throws JsonProcessingException {
        getEvents();
        // TODO : get real events from DB
        return ok(mapper.writeValueAsString(events));
    }

    public Result getEventByID(String id) throws JsonProcessingException {
        getEvents();
        for (Event currEvent: events) {
            if (String.valueOf(currEvent.id).equals(id)) {
                return ok (mapper.writeValueAsString(currEvent));
            }
        }

        return ok(id);
    }

    // TODO: delete it!!!
    private void getEvents() {
        if (events.isEmpty()) {
            events.add(new Event(1,"רכיבה על אופניים", "רכיבה שווה על אופניים. מומלצת לבעלי כושר גופני גבוה", new Point(35, 32), new Date(), "https://upload.wikimedia.org/wikipedia/commons/4/41/Left_side_of_Flying_Pigeon.jpg"));
            events.add(new Event(2,"שחייה צורנית", "שחיה בספורטן העירוני בשלל צורות מעניינות. דרושים משתתפים רבים", new Point(35, 32), new Date(), "http://images.mouse.co.il/storage/8/f/490_fake_gay.jpg"));
            events.add(new Event(3,"ארוחת צהריים בארומה", "פגישה לקפה ומאפה בסניף הוד השרון", new Point(35, 32), new Date(), "http://www.aroma.co.il/_media/images/general/logo.png"));
        }
    }
}
