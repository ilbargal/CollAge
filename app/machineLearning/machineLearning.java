package machineLearning;

import models.Categories;
import models.Events;
import models.Users;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by thecr_000 on 28/05/2016.
 */
public class machineLearning {

    private static machineLearning instance;

    public static machineLearning getInstance(){

        if (instance != null){
            instance = new machineLearning();
        }

        return instance;
    }

    private double matchingCategoryScore;
    private double unMatchingCategoryScore;
    private double matchingUserCategoryScore;
    private double unMatchingUserCategoryScore;

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
     * Return an ArrayList of events ordered by their recommendation score;
     * @param user
     * @param events
     * @return
     */
    public Collection<Events> getRecommendedEventsByUser(Users user, Collection<Events> events){
        ArrayList<mlEvent> mlEvents = new ArrayList<>();

        // Create the list we are going to make our calaculations on
        for(Events e: events){
            mlEvent newE = new mlEvent(e);
        }

        // Make the calculations
        calculateEventesScoreByCategories(user.getCategories(), mlEvents);
        calculateEventsScoreByAttendingUsers(user.getCategories(), mlEvents);

        // Put the events in a new array list
        // Ordered by the score they received
        ArrayList<Events> recEvents = new ArrayList<>();

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

    private void calculateEventsScoreByAttendingUsers(Collection<Categories> userCategories, ArrayList<mlEvent> events){

        for (mlEvent e: events){
            double sumUsersScores = 0;

            for (Users u: e.getEvent().getUsers()){
                double userScore = 0;

                for (Categories c: u.getCategories()) {
                    if (userCategories.contains(c)) {
                        userScore += this.matchingUserCategoryScore;
                    } else {
                        userScore += this.unMatchingUserCategoryScore;
                    }
                }

                // We only acknowledge users that have at least 3
                // Similar categories as the user
                if (userScore > this.matchingUserCategoryScore * 3){
                    sumUsersScores += sumUsersScores;
                }
            }

            e.setScore(e.getScore() + sumUsersScores);
        }
    }

    private void calculateEventScore(Collection<Categories> userCategories, ArrayList<mlEvent> events) {

    }

}
