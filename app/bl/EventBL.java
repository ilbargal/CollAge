package bl;

import models.Event;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
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

    public List<Event>  getAllEvents() {
        // TODO: get from DB and return
        ArrayList<Event> events = new ArrayList<Event>();
        Date currDate = new Date();
        int d = currDate.getDate();
        int m = currDate.getMonth();
        int y = currDate.getYear();
        if (events.isEmpty()) {
            events.add(new Event(1,"רכיבה על אופניים", "רכיבה שווה על אופניים. מומלצת לבעלי כושר גופני גבוה", new Point(35, 32), new Date(y, m, d - 5), new Date(y, m, d - 2), "https://upload.wikimedia.org/wikipedia/commons/4/41/Left_side_of_Flying_Pigeon.jpg"));
            events.add(new Event(2,"שחייה צורנית", "שחיה בספורטן העירוני בשלל צורות מעניינות. דרושים משתתפים רבים", new Point(35, 32), new Date(y, m, d - 5), new Date(y, m, d - 2), "http://images.mouse.co.il/storage/8/f/490_fake_gay.jpg"));
            events.add(new Event(3,"ארוחת צהריים בארומה", "פגישה לקפה ומאפה בסניף הוד השרון", new Point(35, 32), new Date(y, m, d - 5), new Date(y, m, d - 2), "http://www.aroma.co.il/_media/images/general/logo.png"));
        }
        return events;
    }
}
