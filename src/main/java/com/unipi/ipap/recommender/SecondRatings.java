package com.unipi.ipap.recommender;
 /**
 *
 * @author Ilias P
 * @version ver1.0 date: 9/17/2021
 */

import com.unipi.ipap.recommender.model.Movie;
import com.unipi.ipap.recommender.model.Rater;
import com.unipi.ipap.recommender.model.Rating;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;

    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }

    public SecondRatings(String moviefile, String ratingfile) {
        FirstRatings firstRatings = new FirstRatings();
        this.myMovies = new ArrayList<>(firstRatings.loadMovies(moviefile));
        this.myRaters = new ArrayList<>(firstRatings.loadRaters(ratingfile));
    }

    public int getMovieSize() {
        return myMovies.size();
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

        List<Rating> ratings = new ArrayList<>();
        myMovies.forEach(
                movie -> {
                    Rating rating = new Rating(movie.getID(),
                            getAverageByID(movie.getID(), minimalRaters));
                    ratings.add(rating);
                }
        );
        return ratings;
    }

    public String getTitle(String id) {
        AtomicReference<String> movieTitle = new AtomicReference<>();
        myMovies.forEach(
                movie -> {
                    if (movie.getID().equals(id))
                        movieTitle.set(movie.getTitle());
                }
        );
        return (!movieTitle.get().isEmpty())
                ? String.valueOf(movieTitle)
                : "No movie with such ID was found.";
    }

    public String getID(String title) {
        AtomicReference<String> movieId = new AtomicReference<>();
        myMovies.forEach(
                movie -> {
                    if (movie.getTitle().equals(title))
                        movieId.set(movie.getID());
                }
        );
        return (!movieId.get().isEmpty())
                ? String.valueOf(movieId)
                : "NO SUCH TITLE.";
    }

}