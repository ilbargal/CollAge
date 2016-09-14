package machineLearning.KNN;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.typesafe.config.ConfigException;
import machineLearning.*;
import models.Users;

public class Knn {

    private static Knn instance;

    public static void initialize(ArrayList<Users> instances, int k){
        instance = new Knn(instances, k);
    }

    public static Knn getInstance(){
        if (instance == null) {
            throw new NullPointerException("This class ('Knn') has to be initialized first!");
        }

        return instance;
    }

    private ArrayList<Users> instances;
    private ArrayList<Neighbor> distances;
    private ArrayList<ArrayList<Neighbor>> neighbors;
    private int k;

    public ArrayList<Users> getSimilarUsers(Users user) {
        ArrayList<Users> users;
        users = new ArrayList<>();

        for (ArrayList<Neighbor> n : neighbors) {

            for (Neighbor neighbor : n) {
                if (neighbor.getUser().equals(user)) {
                    for (Neighbor neighbor1 : n) {
                        users.add(neighbor1.getUser());
                    }
                }
            }

        }

        return users;
    }

    private Knn(ArrayList<Users> instances, int k){
        this.k = k;
        this.instances = instances;
    }

    /**
     * This function extracts (It removes it from the list and then return it)
     * a user for the the users lists randomly.
     *
     * @return
     */
    public Users extractRandomUser(){
        Users extractedUser;

        if (this.instances.size() == 1){
            extractedUser = instances.get(0);
        } else {
            Random rnd = new Random();
            int rndIndex = rnd.nextInt(this.instances.size() - 1);
            extractedUser = instances.get(rndIndex);
        }

        instances.remove(extractedUser);

        return extractedUser;
    }

    public void run(){
        Users classificationUser;
        int index = 0;
        this.neighbors = new ArrayList<ArrayList<Neighbor>>();

        while (instances.size() > 0){
            classificationUser = extractRandomUser();

            this.distances = calculateDistances(this.instances, classificationUser);
            ArrayList<Neighbor> curNeighbors = getNearestNeighbors(this.distances);
            curNeighbors.add(new Neighbor(classificationUser));
            this.neighbors.add(curNeighbors);

            index++;
        }
    }

    /**
     * Calculate the distance between the current user and all the others
     * @param users
     * @param user
     * @return
     */
    private ArrayList calculateDistances(ArrayList<Users> users, Users user){
        ArrayList<Neighbor> distances = new ArrayList<Neighbor>();
        Neighbor neighbor = null;
        double dist = 0;

        for (int i = 0; i < instances.size(); i++){
            dist = 0;
            Users curUser = instances.get(i);
            neighbor = new Neighbor(curUser);

            dist = neighbor.calcDistance(user);

            distances.add(neighbor);
        }

        // Sort the collection by distance
        Collections.sort(distances);

        return distances;
    }

    /**
     * Extracts the K nearest neighbors
     * @param distances
     * @return
     */
    private ArrayList<Neighbor> getNearestNeighbors(ArrayList<Neighbor> distances){
        ArrayList<Neighbor> neighbors = new ArrayList<Neighbor>();

        for (int i = 0; i < this.k && i < distances.size(); i++) {
            neighbors.add(distances.get(i));
            instances.remove(instances.indexOf(distances.get(i).getUser()));
        }

        return neighbors;
    }
}
