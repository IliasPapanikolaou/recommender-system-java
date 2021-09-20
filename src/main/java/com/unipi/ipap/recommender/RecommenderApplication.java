package com.unipi.ipap.recommender;


/**
 *
 * @author Ilias P
 * @version ver1.0 date: 9/17/2021
 */

public class RecommenderApplication {
    public static void main(String[] args) {

        // First part
        FirstRatings firstRatings = new FirstRatings();
//        firstRatings.testLoadMovies();
//        firstRatings.testLoadRaters();
        MovieRunnerAverage movieRunnerAverage = new MovieRunnerAverage();
//        movieRunnerAverage.printAverageRatings();

        // Second part
//        Movie movie = MovieDatabase.getMovie("0120915");
//        System.out.println(movie);
        MovieRunnerWithFilters movieRunnerWithFilters = new MovieRunnerWithFilters();
//        movieRunnerWithFilters.printAverageRatings();
//        movieRunnerWithFilters.printAverageRatingsByYearAfter();
//        movieRunnerWithFilters.printAverageRatingsByGenre();
//        movieRunnerWithFilters.printAverageRatingsByMinutes();
//        movieRunnerWithFilters.printAverageRatingsByDirectors();
//        movieRunnerWithFilters.printAverageRatingsByYearAfterAndGenre();
//        movieRunnerWithFilters.printAverageRatingsByDirectorAndMinutes();

        // Fourth part
        MovieRunnerSimilarRatings movieRunnerSimilarRatings = new MovieRunnerSimilarRatings();
//        movieRunnerSimilarRatings.printAverageRatings();
//        movieRunnerSimilarRatings.printAverageRatingsByYearAfterAndGenre();

        // Final part
//        Recommender recommender = new RecommendationRunner();
//        List<String> itemsToRate = recommender.getItemsToRate();
//        itemsToRate.forEach(System.out::println);
//
//        recommender.printRecommendationsFor("1");
    }
}
