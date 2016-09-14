package bl;

import ch.qos.logback.classic.db.DBHelper;
import common.DataBaseHandler;
import models.Events;
import models.Users;
import models.UsersToEvents;
import org.hibernate.dialect.MySQL5InnoDBDialect;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.awt.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class EventBL {
    private static EventBL _instance;

    private EventBL() {
    }

    public static EventBL getInstance() {
        if (_instance == null) {
            synchronized (EventBL.class) {
                if (_instance == null) {
                    _instance = new EventBL();
                }
            }
        }
        return _instance;
    }

    public List<Events>  getAllEvents() {
        return DataBaseHandler.getInstance().query("findAllEvents");
    }

    public Events getEventById(Integer id) {
        return DataBaseHandler.getInstance().singleQueryById("findEventById", "evtId", Integer.valueOf(id));
    }

    public void addEvent(Events evt) {
        evt.setStatus(1);
        Users ownerUser = UserBL.getInstance().getUser(evt.getOwner());
        ArrayList<Users> users = new ArrayList<Users>();
        users.add(ownerUser);
        evt.setUsers(users);
        DataBaseHandler.getInstance().Persist(evt);
    }

    public void joinToEvent(String email, int eventId){
        UsersToEvents usersToEvents = new UsersToEvents();
        usersToEvents.setUserMail(email);
        usersToEvents.setEventId(eventId);

        DataBaseHandler.getInstance().Persist(usersToEvents);
    }

    public void leaveEvent(String email, int eventId){
        DataBaseHandler.getInstance().
                UpdateQuery("leaveEvent", email, eventId);
    }

    public void saveEventWithStatus(Events currEvent, String userMail, Integer status)
    {
        // Save user without status of event
        DataBaseHandler.getInstance().Merge(currEvent);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("collageUnit");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Query query = em.createNamedQuery("update collage.users_to_events set status = " + status + " where user_mail = '" + userMail + "' and event_id = " + currEvent.getId());
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}
