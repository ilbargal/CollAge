package models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.util.Date;

@Entity
@Table(name = "EVENTS")
public class Event {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    public int id;
    @NotNull
    @Column(name="NAME")
    public String name;
    @Column(name="DESCRIPTION")
    public String description;
    @Column(name="LOCATION")
    public Point location;
    @Column(name="DATETIME")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy HH:mm")
    public Date datetime;
    @Column(name="IMAGE")
    public String imagePath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Event(int id, String name, String description, Point location, Date datetime, String imagePath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.datetime = datetime;
        this.imagePath = imagePath;
    }

    //public List<Tuple<User, EventStatus>> userStatuses;
}
