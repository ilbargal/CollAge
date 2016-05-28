package models;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Gal on 27-May-16.
 */
@Entity
@Table(name = "users_to_categories", schema = "collage", catalog = "")
@IdClass(UsersToCategoriesPK.class)
public class UsersToCategories {
    private String userMail;
    private Integer categoryId;
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
    @Column(name = "category_id")
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

        UsersToCategories that = (UsersToCategories) o;

        if (userMail != null ? !userMail.equals(that.userMail) : that.userMail != null) return false;
        if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null) return false;
        if (cancelDate != null ? !cancelDate.equals(that.cancelDate) : that.cancelDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userMail != null ? userMail.hashCode() : 0;
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (cancelDate != null ? cancelDate.hashCode() : 0);
        return result;
    }
}