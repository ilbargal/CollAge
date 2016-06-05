package machineLearning;

import models.Events;

import java.awt.*;
import java.util.Collection;

/**
 * Created by Daniel
 * This class represents an event for the machine learning algorithm
 */
public class mlEvent implements Comparable<mlEvent> {

    private Events event;
    private double score;

    public mlEvent(Events event){
        this.event = event;
        this.score = 0;
    }

    public Events getEvent() {
        return event;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public int compareTo(mlEvent o) {

        if (this.getScore() == o.getScore())
            return 0;
        else if (this.getScore() > o.getScore())
            return 1;
        else
            return -1;
    }
}
