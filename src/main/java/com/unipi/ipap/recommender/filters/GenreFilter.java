package com.unipi.ipap.recommender.filters;

import com.unipi.ipap.recommender.MovieDatabase;

public class GenreFilter implements Filter {

    private String genre;

    public GenreFilter(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean satisfies(String id) {
        return MovieDatabase.getGenres(id).contains(genre);
    }
}
