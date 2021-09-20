package com.unipi.ipap.recommender;

import com.unipi.ipap.recommender.filters.*;
import com.unipi.ipap.recommender.model.Rating;

import java.util.Comparator;
import java.util.List;

public class MovieRunnerWithFilters {

    private final int YEAR_AFTER = 1990;
    private final int MINIMAL_RATERS = 3;
    private final String GENRE = "Drama";
    private final int MIN_MINUTES = 90;
    private final int MAX_MINUTES = 180;
    private final String DIRECTOR_LIST =  "Clint Eastwood,Joel Coen," +
            "Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack";

    private final String MOVIES_SOURCE = "ratedmoviesfull.csv";
//    private final String MOVIE_SOURCE = "ratedmovies_short.csv";
    private final String RATINGS_SOURCE = "data/ratings.csv";
//    private final String RATINGS_SOURCE = "data/ratings_short.csv";

    public void printAverageRatings() {

        MovieDatabase.initialize(MOVIES_SOURCE);
        ThirdRatings thirdRatings = new ThirdRatings(RATINGS_SOURCE);

        System.out.println("Number of ratings in the CSV file: " +thirdRatings.getRaterSize());
        System.out.println("Number of movies in the CSV file: " +MovieDatabase.size());

        List<Rating> ratings = thirdRatings.getAverageRatings(MINIMAL_RATERS);
        ratings.sort(Comparator.comparing(Rating::getValue));
        System.out.println("There are " + ratings.size()
                + " movies with at least " + MINIMAL_RATERS
                + " raters");

        ratings.forEach(r ->
                System.out.printf("%.2f %s\n", r.getValue(), MovieDatabase.getTitle(r.getItem()))
        );
    }

    public void printAverageRatingsByYearAfter() {

        MovieDatabase.initialize(MOVIES_SOURCE);
        ThirdRatings thirdRatings = new ThirdRatings(RATINGS_SOURCE);

        System.out.println("Number of ratings in the CSV file: " +thirdRatings.getRaterSize());
        System.out.println("Number of movies in the CSV file: " +MovieDatabase.size());

        YearAfterFilter yearAfterFilter = new YearAfterFilter(YEAR_AFTER);

        List<Rating> averageRatings = thirdRatings.getAverageRatingsByFilter(MINIMAL_RATERS, yearAfterFilter);
        averageRatings.sort(Comparator.comparing(Rating::getValue));
        System.out.println("There are " + averageRatings.size()
                + " movies from " +YEAR_AFTER
                + " and after.");

        averageRatings.forEach(r ->
                System.out.printf("%.2f %s\n", r.getValue(), MovieDatabase.getTitle(r.getItem()))
        );
    }

    public void printAverageRatingsByGenre() {

        MovieDatabase.initialize(MOVIES_SOURCE);
        ThirdRatings thirdRatings = new ThirdRatings(RATINGS_SOURCE);

        System.out.println("Number of ratings in the CSV file: " +thirdRatings.getRaterSize());
        System.out.println("Number of movies in the CSV file: " +MovieDatabase.size());

        GenreFilter genreFilter = new GenreFilter(GENRE);

        List<Rating> averageRatings = thirdRatings.getAverageRatingsByFilter(MINIMAL_RATERS, genreFilter);
        averageRatings.sort(Comparator.comparing(Rating::getValue));
        System.out.println("There are " + averageRatings.size()
                + " with genre: " +GENRE);

        averageRatings.forEach(r ->
                System.out.printf("%.2f %s\n", r.getValue(), MovieDatabase.getTitle(r.getItem()))
        );
    }

    public void printAverageRatingsByDirectors() {

        MovieDatabase.initialize(MOVIES_SOURCE);
        ThirdRatings thirdRatings = new ThirdRatings(RATINGS_SOURCE);

        System.out.println("Number of ratings in the CSV file: " +thirdRatings.getRaterSize());
        System.out.println("Number of movies in the CSV file: " +MovieDatabase.size());

        DirectorFilter directorFilter = new DirectorFilter(DIRECTOR_LIST);

        List<Rating> averageRatings = thirdRatings.getAverageRatingsByFilter(MINIMAL_RATERS, directorFilter);
        averageRatings.sort(Comparator.comparing(Rating::getValue));
        System.out.println("There are " + averageRatings.size()
                + " movies directed from: " + DIRECTOR_LIST);

        averageRatings.forEach(r ->
                System.out.printf("%.2f %s\n", r.getValue(), MovieDatabase.getTitle(r.getItem()))
        );
    }

    public void printAverageRatingsByMinutes() {
        MovieDatabase.initialize(MOVIES_SOURCE);
        ThirdRatings thirdRatings = new ThirdRatings(RATINGS_SOURCE);

        System.out.println("Number of ratings in the CSV file: " +thirdRatings.getRaterSize());
        System.out.println("Number of movies in the CSV file: " +MovieDatabase.size());


        MinutesFilter minutesFilter = new MinutesFilter(MIN_MINUTES, MAX_MINUTES);

        List<Rating> averageRatings = thirdRatings.getAverageRatingsByFilter(MINIMAL_RATERS, minutesFilter);
        averageRatings.sort(Comparator.comparing(Rating::getValue));
        System.out.println("There are " + averageRatings.size() + " that have duration between "
                + MIN_MINUTES + " and " + MAX_MINUTES +" minutes.");

        averageRatings.forEach(r ->
                System.out.printf("%.2f %s\n", r.getValue(), MovieDatabase.getTitle(r.getItem()))
        );
    }

    public void printAverageRatingsByYearAfterAndGenre() {
        MovieDatabase.initialize(MOVIES_SOURCE);
        ThirdRatings thirdRatings = new ThirdRatings(RATINGS_SOURCE);

        System.out.println("Number of ratings in the CSV file: " +thirdRatings.getRaterSize());
        System.out.println("Number of movies in the CSV file: " +MovieDatabase.size());


        YearAfterFilter yearAfterFilter = new YearAfterFilter(YEAR_AFTER);
        GenreFilter genreFilter = new GenreFilter(GENRE);

        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(yearAfterFilter);
        allFilters.addFilter(genreFilter);

        List<Rating> averageRatings = thirdRatings.getAverageRatingsByFilter(MINIMAL_RATERS, allFilters);
        averageRatings.sort(Comparator.comparing(Rating::getValue));
        System.out.println("There are " + averageRatings.size()
                + " " +GENRE + " movies from " + YEAR_AFTER + " and after");

        averageRatings.forEach(r ->
                System.out.printf("%.2f %s\n", r.getValue(), MovieDatabase.getTitle(r.getItem()))
        );
    }

    public void printAverageRatingsByDirectorAndMinutes() {
        MovieDatabase.initialize(MOVIES_SOURCE);
        ThirdRatings thirdRatings = new ThirdRatings(RATINGS_SOURCE);

        System.out.println("Number of ratings in the CSV file: " +thirdRatings.getRaterSize());
        System.out.println("Number of movies in the CSV file: " +MovieDatabase.size());


        DirectorFilter directorFilter = new DirectorFilter(DIRECTOR_LIST);
        MinutesFilter minutesFilter = new MinutesFilter(MIN_MINUTES, MAX_MINUTES);

        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(directorFilter);
        allFilters.addFilter(minutesFilter);

        List<Rating> averageRatings = thirdRatings.getAverageRatingsByFilter(MINIMAL_RATERS, allFilters);
        averageRatings.sort(Comparator.comparing(Rating::getValue));
        System.out.println("There are " + averageRatings.size()
                + " movies directed by either: " + DIRECTOR_LIST +" and have duration between "
                + MIN_MINUTES + " and " + MAX_MINUTES +" minutes.");

        averageRatings.forEach(r ->
                System.out.printf("%.2f %s\n", r.getValue(), MovieDatabase.getTitle(r.getItem()))
        );
    }
}
