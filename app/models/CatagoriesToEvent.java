package models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Gal on 27-May-16.
 */
@Entity
@Table(name = "catagories_to_event", schema = "collage", catalog = "")
@IdClass(CatagoriesToEventPK.class)
public class CatagoriesToEvent {
    private Integer eventId;
    private Integer categoryId;
    private Serializable cancelDate;

    @Id
    @Column(name = "event_id")
    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    @Id
    @Column(name = "category_id")
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "cancel_date")
    public Serializable getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Serializable cancelDate) {
        this.cancelDate = cancelDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CatagoriesToEvent that = (CatagoriesToEvent) o;

        if (eventId != null ? !eventId.equals(that.eventId) : that.eventId != null) return false;
        if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null) return false;
        if (cancelDate != null ? !cancelDate.equals(that.cancelDate) : that.cancelDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = eventId != null ? eventId.hashCode() : 0;
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (cancelDate != null ? cancelDate.hashCode() : 0);
        return result;
    }
}
