package com.unipi.ipap.recommender;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieRunnerAverageTest {

    @Test
    public void printAverageRatingsTest() {
        SecondRatings secondRatings = new SecondRatings("data/ratedmovies_short.csv",
                "data/ratings_short.csv");
        assertEquals(5, secondRatings.getMovieSize());
        assertEquals(5, secondRatings.getRaterSize());
    }

    @Test
    public void getAverageRatingOneMovie() {
        MovieRunnerAverage movieRunnerAverage = new MovieRunnerAverage();
        movieRunnerAverage.getAverageRatingOneMovie();
    }
}
