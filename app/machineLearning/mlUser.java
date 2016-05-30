package machineLearning;

import models.Users;

/**
 * Created by Daniel
 * This class represents a user for the machine learning algorithm
 */
public class mlUser {

    private Users user;
    private double score;

    public mlUser(Users user){
        this.user = user;
        this.score = 0;
    }

    public Users getUser() {
        return user;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
