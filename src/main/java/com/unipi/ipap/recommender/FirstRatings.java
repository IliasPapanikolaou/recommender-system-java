package com.unipi.ipap.recommender;

import com.unipi.ipap.recommender.model.EfficientRater;
import com.unipi.ipap.recommender.model.Movie;
import com.unipi.ipap.recommender.model.Rater;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class FirstRatings {

    // Load movies from CSV
    // Movie Header: "id", "title", "year", "country", "genre", "director", "minutes", "poster"
    public List<Movie> loadMovies(String filename) {

        List<Movie> movies = new ArrayList<>();
        try {
            Reader in = new FileReader(filename);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader("id", "title", "year", "country", "genre", "director",
                            "minutes", "poster")
                    .withFirstRecordAsHeader()
                    .parse(in);
            for (CSVRecord record : records) {
                Movie movie = new Movie(
                        record.get("id"),
                        record.get("title"),
                        record.get("year"),
                        record.get("country"),
                        record.get("genre"),
                        record.get("director"),
                        record.get("minutes"),
                        record.get("poster")
                );
                movies.add(movie);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }

    // Load raters from CSV (rater_id, movie_id, rating, time)
    public List<Rater> loadRaters(String filename) {

        List<Rater> raters = new ArrayList<>();
        Set<String> raterIdSet = new HashSet<>();

        try {
            Reader in = new FileReader(filename);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader("rater_id", "movie_id", "rating", "time")
                    .withFirstRecordAsHeader()
                    .parse(in);

            records.forEach(record -> {
                String raterId = record.get("rater_id");
                String movieId = record.get("movie_id");
                double rating = Double.parseDouble(record.get("rating"));

                if (!raterIdSet.contains(raterId)) {
//                    Rater rater = new PlainRater(raterId);
                    Rater rater = new EfficientRater(raterId);
                    rater.addRating(movieId, rating);
                    raters.add(rater);
                } else {
                    raters.parallelStream().forEach(
                            rater -> {
                                if (rater.getID().equals(raterId))
                                    rater.addRating(movieId, rating);
                            }
                    );
                }
                raterIdSet.add(raterId);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return raters;
    }

    void testLoadMovies() {

//        String filename = "data/ratedmovies_short.csv";
        String filename = "data/ratedmoviesfull.csv";
        List<Movie> movies = loadMovies(filename);
        HashMap<String, Integer> moviesByDirector = new HashMap<>();

        // Determine how many movies include the Comedy genre.
        long countComedies = movies.parallelStream()
                .filter(m -> m.getGenres().contains("Comedy"))
                .count();

        // Determine how many movies are greater than 150 minutes in length
        long countLengthGreaterThan150 = movies.parallelStream()
                .filter(m -> m.getMinutes() > 150)
                .count();

        // Determine the maximum number of movies by any director
        movies.forEach(movie -> {
            String[] directors = movie.getDirector().split(",");
            Arrays.stream(directors).parallel().forEach(director -> {
                director = director.trim();
                if (!moviesByDirector.containsKey(director)) {
                    moviesByDirector.put(director, 1);
                } else {
                    moviesByDirector.put(director, moviesByDirector.get(director) + 1);
                }
                moviesByDirector.put(director, moviesByDirector.get(director) + 1);
            });
        });

        System.out.println("-----------------------------------");
        System.out.println("Total number of movies: " + movies.size());
        System.out.println("-----------------------------------");
        System.out.println("Total Comedies: " + countComedies);
        System.out.println("-----------------------------------");
        System.out.println("Total Movies Longer than 150 minutes: " + countLengthGreaterThan150);
        System.out.println("-----------------------------------");
        moviesByDirector.forEach(
                (key, value) -> System.out.println(key + " number of movies: " + value)
        );
        System.out.println("-----------------------------------");
        System.out.println("Number of Directors: " + moviesByDirector.size());

        // Determine the maximum number of movies by any director
        int max = moviesByDirector.entrySet().parallelStream()
                .mapToInt(Map.Entry::getValue)
                .max()
                .orElse(-1);
        System.out.println("-----------------------------------");
        System.out.println("Maximum number of movies by any director: " +max);

    }

    void testLoadRaters() {
//        String filename = "data/ratings_short.csv";
        String filename = "data/ratings.csv";
        List<Rater> raters = loadRaters(filename);
        /*
         * Total number of raters. Then for each rater,
         * print the rater’s ID and the number of ratings they did on one line,
         * followed by each rating (both the movie ID and the rating given) on a separate line
         */
        System.out.println("-----------------------------------");
        System.out.println("Raters List size: " + raters.size());
        System.out.println("-----------------------------------");
        raters.forEach(
                rater -> {
                    System.out.println("Rater id: " + rater.getID()
                            + ". Number of ratings: " + rater.numRatings());

                    rater.getItemsRated().forEach(
                            rating -> System.out.println("Movie id: " + rating
                                    + " rating: " + rater.getRating(rating))
                    );
                }
        );

        // Number of ratings for a particular rater
        String rater_id = "193";
        Optional<Rater> rater = raters.parallelStream().filter(
                r -> r.getID().equals(rater_id)
        ).reduce((a, b) -> {
            throw new IllegalArgumentException("Multiple elements" + a + ", " + b);

        });
        System.out.println("-----------------------------------");
        System.out.println("User " + rater_id + " has " + rater.orElseThrow().numRatings() + " ratings");

        // Determine how many raters have the maximum number of ratings and who those raters are
        int max = raters.parallelStream()
                .mapToInt(Rater::numRatings)
                .max()
                .orElse(-1);

        List<Rater> ratersMaxRatings = raters.parallelStream()
                .filter(r -> r.numRatings() == max)
                .collect(Collectors.toList());

        System.out.println("-----------------------------------");
        System.out.println(ratersMaxRatings.size() + " of the raters have " + max + " ratings:");
        ratersMaxRatings.forEach(r -> System.out.println("Rater id: " + r.getID()));


        // Number of ratings a particular movie has
        String movieId = "1798709";
        AtomicInteger atomicInteger = new AtomicInteger();
        raters.parallelStream().forEach(
                r -> {
                    r.getItemsRated().parallelStream().forEach(
                            m -> {
                                if (m.equals(movieId)) atomicInteger.incrementAndGet();
                            });
                });
        System.out.println("-----------------------------------");
        System.out.println("Movie with id: " + movieId + " has " + atomicInteger.get() + " ratings");

        // Determine how many different movies have been rated by all these raters
        Set<String> moviesRated = new HashSet<>();
        raters.forEach(
                r -> moviesRated.addAll(r.getItemsRated()));
        System.out.println("Total movies rated: " + moviesRated.size());
    }
}
