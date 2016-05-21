package bl;

public class EventBL {
    private static EventBL _instance;

    protected EventBL() {
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
}
