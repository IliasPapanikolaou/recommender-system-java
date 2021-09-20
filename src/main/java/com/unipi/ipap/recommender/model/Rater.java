package com.unipi.ipap.recommender.model;
/*
 *
 * @author Ilias P
 * @version ver1.0 date: 9/17/2021
 */

import java.util.*;

public interface Rater {

    void addRating(String item, double rating);

    boolean hasRating(String item);

    String getID();

    double getRating(String item);

    int numRatings();

    ArrayList<String> getItemsRated();

}
