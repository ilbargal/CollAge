package models;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Gal on 27-May-16.
 */
@Entity
@NamedQueries({
    @NamedQuery(name="findEventById", query="SELECT e from Events e where e.id = :evtId"),
    @NamedQuery(name="findAllEvents", query="SELECT e from Events e"),
})
public class Events {
    private Integer id;
    private String name;
    private String owner;
    private Integer status;
    private String description;
    private String location;
    private Timestamp datetime;
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
    @Column(name = "owner")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "datetime")
    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
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

        Events events = (Events) o;

        if (id != null ? !id.equals(events.id) : events.id != null) return false;
        if (name != null ? !name.equals(events.name) : events.name != null) return false;
        if (owner != null ? !owner.equals(events.owner) : events.owner != null) return false;
        if (status != null ? !status.equals(events.status) : events.status != null) return false;
        if (description != null ? !description.equals(events.description) : events.description != null) return false;
        if (location != null ? !location.equals(events.location) : events.location != null) return false;
        if (datetime != null ? !datetime.equals(events.datetime) : events.datetime != null) return false;
        if (cancelDate != null ? !cancelDate.equals(events.cancelDate) : events.cancelDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (datetime != null ? datetime.hashCode() : 0);
        result = 31 * result + (cancelDate != null ? cancelDate.hashCode() : 0);
        return result;
    }
}
