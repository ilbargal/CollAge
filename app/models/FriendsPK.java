package models;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Gal on 27-May-16.
 */
public class FriendsPK implements Serializable {
    private String firstUser;
    private String secondUser;

    @Column(name = "first_user")
    @Id
    public String getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(String firstUser) {
        this.firstUser = firstUser;
    }

    @Column(name = "second_user")
    @Id
    public String getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(String secondUser) {
        this.secondUser = secondUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FriendsPK friendsPK = (FriendsPK) o;

        if (firstUser != null ? !firstUser.equals(friendsPK.firstUser) : friendsPK.firstUser != null) return false;
        if (secondUser != null ? !secondUser.equals(friendsPK.secondUser) : friendsPK.secondUser != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstUser != null ? firstUser.hashCode() : 0;
        result = 31 * result + (secondUser != null ? secondUser.hashCode() : 0);
        return result;
    }
}
