package models;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Gal on 27-May-16.
 */
@Entity
@IdClass(FriendsPK.class)
public class Friends {
    private String firstUser;
    private String secondUser;
    private Timestamp cancelDate;

    @Id
    @Column(name = "first_user")
    public String getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(String firstUser) {
        this.firstUser = firstUser;
    }

    @Id
    @Column(name = "second_user")
    public String getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(String secondUser) {
        this.secondUser = secondUser;
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

        Friends friends = (Friends) o;

        if (firstUser != null ? !firstUser.equals(friends.firstUser) : friends.firstUser != null) return false;
        if (secondUser != null ? !secondUser.equals(friends.secondUser) : friends.secondUser != null) return false;
        if (cancelDate != null ? !cancelDate.equals(friends.cancelDate) : friends.cancelDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstUser != null ? firstUser.hashCode() : 0;
        result = 31 * result + (secondUser != null ? secondUser.hashCode() : 0);
        result = 31 * result + (cancelDate != null ? cancelDate.hashCode() : 0);
        return result;
    }
}
