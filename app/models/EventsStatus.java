package models;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Gal on 27-May-16.
 */
@Entity
@Table(name = "events_status", schema = "collage", catalog = "")
public class EventsStatus {
    private Integer id;
    private String name;
    private Timestamp cancelDate;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

        EventsStatus that = (EventsStatus) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (cancelDate != null ? !cancelDate.equals(that.cancelDate) : that.cancelDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (cancelDate != null ? cancelDate.hashCode() : 0);
        return result;
    }
}