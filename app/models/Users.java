package models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Gal on 27-May-16.
 */
@Entity
@NamedQueries({
        @NamedQuery(name="findAllUsers", query="SELECT u FROM Users u"),
        @NamedQuery(name="findUserByEmail", query="SELECT u FROM Users u WHERE u.mail LIKE :email")
})
public class Users {
    private String mail;
    private String password;
    private String type;
    private String firstName;
    private String lastName;
    private String gender;
    private Date birthday;
    private String address;
    private String phone;
    private String job;
    private String profilePic;
    private Timestamp cancelDate;
    private Collection<Categories> categories = new ArrayList<Categories>();
    private String description;
    private Collection<Events> events;

    @Id
    @Column(name = "mail")
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) { this.type = type; }

    @Basic
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "birthday")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "job")
    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Basic
    @Column(name = "profile_pic")
    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
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

        Users users = (Users) o;

        if (mail != null ? !mail.equals(users.mail) : users.mail != null) return false;
        if (password != null ? !password.equals(users.password) : users.password != null) return false;
        if (firstName != null ? !firstName.equals(users.firstName) : users.firstName != null) return false;
        if (lastName != null ? !lastName.equals(users.lastName) : users.lastName != null) return false;
        if (gender != null ? !gender.equals(users.gender) : users.gender != null) return false;
        if (birthday != null ? !birthday.equals(users.birthday) : users.birthday != null) return false;
        if (address != null ? !address.equals(users.address) : users.address != null) return false;
        if (phone != null ? !phone.equals(users.phone) : users.phone != null) return false;
        if (job != null ? !job.equals(users.job) : users.job != null) return false;
        if (profilePic != null ? !profilePic.equals(users.profilePic) : users.profilePic != null) return false;
        if (cancelDate != null ? !cancelDate.equals(users.cancelDate) : users.cancelDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mail != null ? mail.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (job != null ? job.hashCode() : 0);
        result = 31 * result + (profilePic != null ? profilePic.hashCode() : 0);
        result = 31 * result + (cancelDate != null ? cancelDate.hashCode() : 0);
        return result;
    }

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "users_to_categories",
            schema = "collage",
            joinColumns = @JoinColumn(name = "user_mail", referencedColumnName = "mail", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id", table = "CATEGORIES",nullable = false))
    public Collection<Categories> getCategories() {
        return categories;
    }

    public void setCategories(Collection<Categories> categories) {
        this.categories = categories;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    @ManyToMany
//    @JoinTable(name = "users_to_events",
//            schema = "collage",
//            joinColumns = @JoinColumn(name = "user_mail", referencedColumnName = "mail", nullable = false),
//            inverseJoinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false))
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    @JsonBackReference
    public Collection<Events> getEvents() {
        return events;
    }

    public void setEvents(Collection<Events> events) {
        this.events = events;
    }
}
