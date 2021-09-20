package com.unipi.ipap.recommender;

import com.unipi.ipap.recommender.model.Rating;

import java.util.Comparator;
import java.util.List;

public class MovieRunnerAverage {

    public void printAverageRatings() {
        SecondRatings secondRatings = new SecondRatings("data/ratedmoviesfull.csv",
                "data/ratings.csv");
//        SecondRatings secondRatings = new SecondRatings("data/ratedmovies_short.csv",
//                "data/ratings_short.csv");
        System.out.println("Number of movies in the CSV file: " +secondRatings.getMovieSize());
        System.out.println("Number of ratings in the CSV file: " +secondRatings.getRaterSize());

        // Define the number of minimum raters
        List<Rating> ratings = secondRatings.getAverageRatings(3);
        ratings.sort(Comparator.comparing(Rating::getValue));
        ratings.forEach(rating -> {
            if (rating.getValue() > 0)
                System.out.printf("%.4f %s\n", rating.getValue(), secondRatings.getTitle(rating.getItem()));

        });
    }

    // Print out the average ratings for a specific movie title
    public void getAverageRatingOneMovie() {
        SecondRatings secondRatings = new SecondRatings("data/ratedmovies_short.csv",
                "data/ratings_short.csv");

        String title = "The Godfather";
        String movieId = secondRatings.getID(title);

        List<Rating> ratings = secondRatings.getAverageRatings(3);
        ratings.forEach(rating -> {
            if (rating.getItem().equals(movieId))
                System.out.println(title +" has average rating: " +rating.getValue());
        });
    }

}
