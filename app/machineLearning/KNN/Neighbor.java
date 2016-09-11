package machineLearning.KNN;

import machineLearning.mlConsts;
import machineLearning.mlUser;
import models.Categories;
import models.Users;

import java.util.ArrayList;

public class Neighbor implements Comparable<Neighbor>{

    private Users user;
    private double distance;

    public Neighbor(Users user, double distance) {
        this.user = user;
        this.distance = distance;
    }

    public Neighbor(Users curUser) {
        this.user = curUser;
        this.distance = 0;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * Calculate the distance between 'this' and the give user
     * @param user
     * @return
     */
    public double calcDistance(Users user){
        double distance = 0;

        for (Categories c: this.getUser().getCategories()) {
            ArrayList<Categories> userCategories = new ArrayList<Categories>(user.getCategories());

            if (userCategories.contains(c)) {
                distance += mlConsts.matchingUserCategoryScore;
            } else {
                distance += mlConsts.unMatchingUserCategoryScore;
            }
        }

        return distance;
    }

    @Override
    public int compareTo(Neighbor o) {
        return Double.compare(this.distance, o.distance);
    }
}
