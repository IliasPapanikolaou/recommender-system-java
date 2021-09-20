package com.unipi.ipap.recommender;
/**
 *
 * @author Ilias P
 * @version ver1.0 date: 9/17/2021
 */

import com.unipi.ipap.recommender.filters.Filter;
import com.unipi.ipap.recommender.filters.TrueFilter;
import com.unipi.ipap.recommender.model.Rater;
import com.unipi.ipap.recommender.model.Rating;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ThirdRatings {
    private ArrayList<Rater> myRaters;

    public ThirdRatings() {
        // default constructor
        this("ratings.csv");
    }

    public ThirdRatings(String ratingfile) {
        FirstRatings firstRatings = new FirstRatings();
        this.myRaters = new ArrayList<>(firstRatings.loadRaters(ratingfile));
    }

    public int getRaterSize() {
        return myRaters.size();
    }

    // Average movie rating by ID
    // movieId: id, number of raters: minimalRaters
    private double getAverageByID(String id, int minimalRaters) {

        AtomicReference<Double> sum = new AtomicReference<>(0.0);
        AtomicInteger count = new AtomicInteger();

        myRaters.forEach(
                rater -> {
                    if (rater.hasRating(id)) {
                        sum.updateAndGet(v -> v + rater.getRating(id));
                        count.addAndGet(1);
                    }
                }
        );
        return (count.get() >= minimalRaters) ? sum.get() / count.get() : 0.0;
    }

    // Average rating for every movie that has been rated by at least minimalRaters rater
    public List<Rating> getAverageRatings(int minimalRaters) {

        List<String> movies = MovieDatabase.filterBy(new TrueFilter());
        List<Rating> avgRatings = new ArrayList<>();

        movies.forEach(
                id -> {
                    double avg = Math.round(getAverageByID(id, minimalRaters) * 100.0) / 100.0;
                    if (avg != 0.0) {
                        Rating rating = new Rating(id, avg);
                        avgRatings.add(rating);
                    }
                }
        );
        return avgRatings;
    }

    public List<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
        List<String> filteredMovies = MovieDatabase.filterBy(filterCriteria);
        List<Rating> averageRatings = new ArrayList<>();

        filteredMovies.forEach(
                id -> {
                    double avg = Math.round(getAverageByID(id, minimalRaters) * 100.0) / 100.0;
                    if (avg != 0.0) {
                        Rating rating = new Rating (id, avg);
                        averageRatings.add(rating);
                    }
                }
        );
        return averageRatings;
    }
}
