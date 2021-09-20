package com.unipi.ipap.recommender.filters;

import com.unipi.ipap.recommender.MovieDatabase;

public class DirectorFilter implements Filter {

    String directorsList;

    public DirectorFilter(String directorsList) {
        this.directorsList = directorsList;
    }

    @Override
    public boolean satisfies(String id) {
        String[] directorsSplit = directorsList.split(",");
        for (int i=0; i < directorsSplit.length; i++) {
            if (MovieDatabase.getDirector(id).indexOf(directorsSplit[i]) != -1) {
                return true;
            }
        }
        return false;
    }
}
