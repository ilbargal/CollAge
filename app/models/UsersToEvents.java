package models;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Gal on 27-May-16.
 */
@Entity
@Table(name = "users_to_events", schema = "collage", catalog = "")
@IdClass(UsersToEventsPK.class)
public class UsersToEvents {
    private String userMail;
    private Integer eventId;
    private Timestamp cancelDate;

    @Id
    @Column(name = "user_mail")
    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    @Id
    @Column(name = "event_id")
    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    @Basic
    @Column(name = "cancel_date")
    public Timestamp getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Timestamp cancelDate) {
        this.cancelDate = cancelDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersToEvents that = (UsersToEvents) o;

        if (userMail != null ? !userMail.equals(that.userMail) : that.userMail != null) return false;
        if (eventId != null ? !eventId.equals(that.eventId) : that.eventId != null) return false;
        if (cancelDate != null ? !cancelDate.equals(that.cancelDate) : that.cancelDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userMail != null ? userMail.hashCode() : 0;
        result = 31 * result + (eventId != null ? eventId.hashCode() : 0);
        result = 31 * result + (cancelDate != null ? cancelDate.hashCode() : 0);
        return result;
    }
}
