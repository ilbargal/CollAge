package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.util.ArrayList;

@Entity
@Table(name = "USERS")
public class User {
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = "MAIL")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String id;
    @Column(name="FIRST_NAME")
    private String firstName;
    @Column(name="LAST_NAME")
    private String lastName;
    @Column(name="AGE")
    private float age;
    @Column(name="GENDER")
    private boolean gender;
    @Column(name="CURRENT_JOB")
    private String currentJob;
    @Column(name="ADDRESS")
    private String address;
    @Column(name="PHONE")
    private String phone;
    @Column(name="BIRTHDATE")
    private String birthday;
    @JsonIgnore
    @Column(name="PASSWORD")
    private String password;

    @ManyToMany
    public ArrayList<String> friends;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public float getAge() {
        return age;
    }

    public void setAge(float age) {
        this.age = age;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(String currentJob) {
        this.currentJob = currentJob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
