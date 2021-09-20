package com.unipi.ipap.recommender;

import com.unipi.ipap.recommender.filters.*;
import com.unipi.ipap.recommender.model.Rating;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

public class MovieRunnerSimilarRatings {

    private final String USER_ID = "168";
    private final int NUM_OF_SIMILAR_RATERS = 10;
    private final int MINIMAL_RATERS = 3;
    private final int YEAR_AFTER = 1975;
    private final String GENRE = "Drama";
    private final int MIN_MINUTES = 80;
    private final int MAX_MINUTES = 160;
    private final String DIRECTOR_LIST =  "Clint Eastwood,J.J. Abrams,Alfred Hitchcock," +
            "Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh";

    private final String MOVIES_SOURCE = "ratedmoviesfull.csv";
//    private final String MOVIE_SOURCE = "ratedmovies_short.csv";
    private final String RATINGS_SOURCE = "ratings.csv";
//    private final String RATINGS_SOURCE = "data/ratings_short.csv";

    public void printAverageRatings() {

        MovieDatabase.initialize(MOVIES_SOURCE);
        FourthRatings fourthRatings = new FourthRatings(RATINGS_SOURCE);

        System.out.println("Number of ratings in the CSV file: " +RaterDatabase.size());
        System.out.println("Number of movies in the CSV file: " +MovieDatabase.size());

        List<Rating> ratings = fourthRatings.getAverageRatings(MINIMAL_RATERS);
        ratings.sort(Comparator.comparing(Rating::getValue));
        System.out.println("There are " + ratings.size()
                + " movies with at least " + MINIMAL_RATERS
                + " raters");

        ratings.forEach(r ->
                System.out.printf("%.2f %s\n", r.getValue(), MovieDatabase.getTitle(r.getItem()))
        );
    }

    public void printAverageRatingsByYearAfterAndGenre() {
        MovieDatabase.initialize(MOVIES_SOURCE);
        FourthRatings fourthRatings = new FourthRatings(RATINGS_SOURCE);

        System.out.println("Number of ratings in the CSV file: " +RaterDatabase.size());
        System.out.println("Number of movies in the CSV file: " +MovieDatabase.size());


        YearAfterFilter yearAfterFilter = new YearAfterFilter(YEAR_AFTER);
        GenreFilter genreFilter = new GenreFilter(GENRE);

        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(yearAfterFilter);
        allFilters.addFilter(genreFilter);

        List<Rating> averageRatings = fourthRatings.getAverageRatingsByFilter(MINIMAL_RATERS, allFilters);
        averageRatings.sort(Comparator.comparing(Rating::getValue));
        System.out.println("There are " + averageRatings.size()
                + " " +GENRE + " movies from " + YEAR_AFTER + " and after");

        averageRatings.forEach(r ->
                System.out.printf("%.2f %s\n", r.getValue(), MovieDatabase.getTitle(r.getItem()))
        );

    }

    @Test
    public void  printSimilarRatings() {
        MovieDatabase.initialize(MOVIES_SOURCE);
        FourthRatings fourthRatings = new FourthRatings(RATINGS_SOURCE);

        System.out.println("Number of ratings in the CSV file: " +RaterDatabase.size());
        System.out.println("Number of movies in the CSV file: " +MovieDatabase.size());

        List<Rating> similarRatings = fourthRatings.getSimilarRatings(
                USER_ID, NUM_OF_SIMILAR_RATERS, MINIMAL_RATERS);

        System.out.println("Found " +similarRatings.size() + " movies recommended for User-ID: "
                + USER_ID + " with at least " + MINIMAL_RATERS + " ratings.\n" + NUM_OF_SIMILAR_RATERS
                +" closest raters are considered.") ;

        similarRatings.forEach(
                rating -> System.out.println(rating.getValue() +" " +MovieDatabase.getTitle(rating.getItem()))
        );
    }

    @Test
    public void  printSimilarRatingsByGenre() {
        MovieDatabase.initialize(MOVIES_SOURCE);
        FourthRatings fourthRatings = new FourthRatings(RATINGS_SOURCE);

        System.out.println("Number of ratings in the CSV file: " +RaterDatabase.size());
        System.out.println("Number of movies in the CSV file: " +MovieDatabase.size());

        GenreFilter genreFilter = new GenreFilter(GENRE);

        List<Rating> similarRatings = fourthRatings.getSimilarRatingsByFilter(
                USER_ID, NUM_OF_SIMILAR_RATERS, MINIMAL_RATERS, genreFilter);

        System.out.println("Found " +similarRatings.size() +" " + GENRE + " movies recommended for User-ID: "
                + USER_ID + " with at least " + MINIMAL_RATERS + " ratings.\n" + NUM_OF_SIMILAR_RATERS
                +" closest raters are considered.") ;

        similarRatings.forEach(
                rating -> System.out.println(rating.getValue() +" " +MovieDatabase.getTitle(rating.getItem()))
        );
    }

    @Test
    public void  printSimilarRatingsByDirector() {
        MovieDatabase.initialize(MOVIES_SOURCE);
        FourthRatings fourthRatings = new FourthRatings(RATINGS_SOURCE);

        System.out.println("Number of ratings in the CSV file: " +RaterDatabase.size());
        System.out.println("Number of movies in the CSV file: " +MovieDatabase.size());

        DirectorFilter directorFilter = new DirectorFilter(DIRECTOR_LIST);

        List<Rating> similarRatings = fourthRatings.getSimilarRatingsByFilter(
                USER_ID, NUM_OF_SIMILAR_RATERS, MINIMAL_RATERS, directorFilter);

        System.out.println("Found " +similarRatings.size() + " movies directed by either: " + DIRECTOR_LIST
                + " recommended for User-ID: " + USER_ID + " with at least " + MINIMAL_RATERS
                + " ratings.\n" + NUM_OF_SIMILAR_RATERS +" closest raters are considered.") ;

        similarRatings.forEach(
                rating -> System.out.println(rating.getValue() +" " +MovieDatabase.getTitle(rating.getItem()))
        );
    }

    @Test
    public void  printSimilarRatingsByGenreAndMinutes() {
        MovieDatabase.initialize(MOVIES_SOURCE);
        FourthRatings fourthRatings = new FourthRatings(RATINGS_SOURCE);

        System.out.println("Number of ratings in the CSV file: " +RaterDatabase.size());
        System.out.println("Number of movies in the CSV file: " +MovieDatabase.size());

        GenreFilter genreFilter = new GenreFilter(GENRE);
        MinutesFilter minutesFilter = new MinutesFilter(MIN_MINUTES, MAX_MINUTES);

        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(genreFilter);
        allFilters.addFilter(minutesFilter);

        List<Rating> similarRatings = fourthRatings.getSimilarRatingsByFilter(
                USER_ID, NUM_OF_SIMILAR_RATERS, MINIMAL_RATERS, allFilters);

        System.out.println("Found " +similarRatings.size() +" " + GENRE + " movies with duration between "
                + MIN_MINUTES + " AND " +MAX_MINUTES + " minutes, recommended for User-ID: "
                + USER_ID + " with at least " + MINIMAL_RATERS + " ratings.\n" + NUM_OF_SIMILAR_RATERS
                +" closest raters are considered.") ;

        similarRatings.forEach(
                rating -> System.out.println(rating.getValue() +" " +MovieDatabase.getTitle(rating.getItem()))
        );
    }

    @Test
    public void  printSimilarRatingsByYearAfterAndMinutes() {
        MovieDatabase.initialize(MOVIES_SOURCE);
        FourthRatings fourthRatings = new FourthRatings(RATINGS_SOURCE);

        System.out.println("Number of ratings in the CSV file: " +RaterDatabase.size());
        System.out.println("Number of movies in the CSV file: " +MovieDatabase.size());

        YearAfterFilter yearAfterFilter = new YearAfterFilter(YEAR_AFTER);
        MinutesFilter minutesFilter = new MinutesFilter(MIN_MINUTES, MAX_MINUTES);

        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(yearAfterFilter);
        allFilters.addFilter(minutesFilter);

        List<Rating> similarRatings = fourthRatings.getSimilarRatingsByFilter(
                USER_ID, NUM_OF_SIMILAR_RATERS, MINIMAL_RATERS, allFilters);

        System.out.println("Found " +similarRatings.size() + " movies from " +YEAR_AFTER
                + " with duration between " + MIN_MINUTES + " AND " +MAX_MINUTES
                + " minutes, recommended for User-ID: " + USER_ID
                + " with at least " + MINIMAL_RATERS + " ratings.\n" + NUM_OF_SIMILAR_RATERS
                +" closest raters are considered.") ;

        similarRatings.forEach(
                rating -> System.out.println(rating.getValue() +" " +MovieDatabase.getTitle(rating.getItem()))
        );
    }
}
