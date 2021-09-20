package com.unipi.ipap.recommender.filters;

import com.unipi.ipap.recommender.MovieDatabase;

public class MinutesFilter implements Filter {

    private int minMin;
    private int minMax;

    public MinutesFilter(int minMin, int minMax) {
        this.minMin = minMin;
        this.minMax = minMax;
    }

    @Override
    public boolean satisfies(String id) {
        int movieDuration = MovieDatabase.getMinutes(id);
        return movieDuration >= minMin && movieDuration <= minMax;
    }
}
