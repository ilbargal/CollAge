package controllers;
import bl.EventBL;
import com.fasterxml.jackson.core.JsonProcessingException;
import common.Utils;
import models.Event;
import models.EventsEntity;
import play.db.jpa.JPA;
import play.mvc.Controller;
import play.mvc.Result;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivitiesController extends Controller {

    public Result getAllActivities() throws JsonProcessingException {

        try {
            // TODO: FIX THIS!!!!!!!!!!!!
            //EntityManagerFactory emf = Persistence.createEntityManagerFactory("collageUnit");
            //EntityManager em = emf.createEntityManager();
            //Query q =  em.createQuery("select ee from EventsEntity ee");
            //System.out.println(q);

            //List<Event> events = EventBL.getInstance().getAllEvents();
            //String JsonValue = Utils.convertObjectToJsonString(events);
            return ok();
            //return ok(Utils.convertObjectToJsonString(events));
        } catch (Exception e) {
            e.printStackTrace();
            return internalServerError(e.getStackTrace()[0].toString());
        }



    }

    public Result getActivity(String id) throws JsonProcessingException {
        //List<Event> events = EventBL.getInstance().getAllEvents();
        //for (Event currEvent: events) {
        //    if (String.valueOf(currEvent.id).equals(id)) {
        //        return ok (Utils.convertObjectToJsonString(currEvent));
        //    }
        //}

        return ok();
    }
}
