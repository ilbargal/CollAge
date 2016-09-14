package machineLearning;

import machineLearning.KNN.Knn;
import machineLearning.KNN.Neighbor;
import models.Categories;
import models.Events;
import models.Users;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Instant;
import org.joda.time.Interval;

import java.util.*;

/**
 * This class is used in order to calculate which events (activities)
 * the user will probably like.
 */
public class machineLearning {

    private static machineLearning instance;

    public static machineLearning getInstance(){

        if (instance == null){
            instance = new machineLearning();
        }

        return instance;
    }

    private double matchingCategoryScore;
    private double unMatchingCategoryScore;
    private double matchingUserCategoryScore;
    private double unMatchingUserCategoryScore;

    private DateTime lastKnnRefresh;

    /**
     * Default constructor.
     * It's private because this class is a singleton.
     */
    private machineLearning(){
        this.matchingCategoryScore = 1;
        this.unMatchingCategoryScore = -0.5;

        this.matchingUserCategoryScore = 0.2;
        this.unMatchingUserCategoryScore = -0.1;
    }

    /**
     * Checks if we need to re-run the KNN algorithm
     * by checking if enough time has passed
     * @return
     */
    public boolean needToRefresh(){
        if (lastKnnRefresh == null ||
                new Duration(lastKnnRefresh, new Instant()).isLongerThan(new Duration(mlConsts.refreshInterval))){
            return true;
        }

        return false;
    }

    /**
     * Run the KNN alogrithm for the given users
     * @param users
     */
    public void refreshKnn(ArrayList<Users> users){
        Knn.initialize(users, mlConsts.k);
        Knn.getInstance().run();
    }

    /**
     * The main function of this class.
     * Returns an ArrayList of events ordered by their recommendation score;
     * @param user
     * @param events
     * @return
     */
    public Collection<Events> getRecommendedEventsByUser(Users user, Collection<Events> events){
        ArrayList<mlEvent> mlEvents = new ArrayList<>();

        // Create the list we are going to make our calculations on
        for(Events e: events){
            mlEvent newE = new mlEvent(e);
            mlEvents.add(newE);
        }

        // Make the calculations
        calculateEventesScoreByCategories(user.getCategories(), mlEvents);
        calculateEventsScoreByAttendingUsers(user.getCategories(), mlEvents, user);

        // Put the events in a new array list
        // Ordered by the score they received
        ArrayList<Events> recEvents = new ArrayList<>();

        Collections.sort(mlEvents);
        Collections.reverse(mlEvents);

        for (mlEvent e: mlEvents){
            Events event = e.getEvent();
            recEvents.add(event);
        }

        return recEvents;
    }

    private void calculateEventesScoreByCategories(Collection<Categories> categories, ArrayList<mlEvent> events){
        for (mlEvent e: events){
            for (Categories c: categories){

                // Increase the event score by 1 for each matching category
                if (e.getEvent().getCategories().contains(c)){
                    e.setScore(e.getScore() + this.matchingCategoryScore);
                } else {
                    e.setScore(e.getScore() + this.unMatchingCategoryScore);
                }
            }
        }
    }

    /**
     * Calculates the score of the event by most similar attending users (got from KNN)
     * @param userCategories
     * @param events
     */
    private void calculateEventsScoreByAttendingUsers(Collection<Categories> userCategories, ArrayList<mlEvent> events,
                                                      Users user){
        ArrayList<Users> similarUserList = Knn.getInstance().getSimilarUsers(user);

        for (mlEvent e: events){
            double sumUsersScores = 0;

            for (Users u: e.getEvent().getUsers()){
                double userScore = 0;

                if (similarUserList.contains(u)){
                    sumUsersScores++;
                }
            }

            e.setScore(e.getScore() + sumUsersScores);
        }
    }

}
