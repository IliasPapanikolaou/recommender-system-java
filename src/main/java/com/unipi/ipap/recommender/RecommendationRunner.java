package com.unipi.ipap.recommender;

import com.unipi.ipap.recommender.filters.TrueFilter;
import com.unipi.ipap.recommender.model.Rating;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class RecommendationRunner implements Recommender{

    @Override
    public ArrayList<String> getItemsToRate() {
        ArrayList<String> itemsToRate = new ArrayList<>();
        List<String> movies = MovieDatabase.filterBy(new TrueFilter());

        IntStream.range(0,20).forEach(
                num -> {
                    int randInt = new Random().nextInt(movies.size());
                    // Do not put the same movie twice - check
                    if (!itemsToRate.contains(movies.get(randInt)))
                        itemsToRate.add(movies.get(randInt));
                }
        );
        return itemsToRate;
    }

    @Override
    public void printRecommendationsFor(String webRaterID) {

        FourthRatings fourthRatings = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull");
        RaterDatabase.initialize("ratings");

        System.out.println("<p>Read data for " + Integer.toString(RaterDatabase.size()) + " raters</p>");
        System.out.println("<p>Read data for " + Integer.toString(MovieDatabase.size()) + " movies</p>");

        // variable
        int numSimilarRaters = 50;
        // variable
        int minNumOfRatings = 5;

        List<Rating> similarRatings = fourthRatings.getSimilarRatings(webRaterID, numSimilarRaters, minNumOfRatings);

        if (similarRatings.size() == 0) {
            System.out.println("No matching movies were found");
        } else {
            String header = ("<table> <tr> <th>Movie Title</th> <th>Rating Value</th>  <th>Genres</th> </tr>");
            String body = "";
            for (Rating rating : similarRatings) {
                body += "<tr> <td>" + MovieDatabase.getTitle(rating.getItem()) + "</td> <td>"
                        + Double.toString(rating.getValue()) + "</td> <td>" + MovieDatabase.getGenres(rating.getItem())
                        + "</td> </tr> ";
            }
            System.out.println(header  + body + "</table>");
        }
    }
}
