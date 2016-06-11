package bl;

import ch.qos.logback.classic.db.DBHelper;
import common.DataBaseHandler;
import models.Events;
import models.Users;

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
}
