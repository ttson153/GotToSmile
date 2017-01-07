package com.google.android.gms.samples.vision.face.facetracker.util;

/**
 * Created by beekill on 1/7/17.
 */

public class MoviePosterUrl {

    public enum MoviePosterSize {
        SMALL,
        LARGE,
        ORIGINAL
    }

    private static final String MOVIE_POSTER_BASE_URL = "http://image.tmdb.org/t/p/";


    public static String getPosterUrl(String path, MoviePosterSize size) {
        return getFullUrl(path, getSize(size));
    }

    private static String getSize(MoviePosterSize size) {
        switch (size) {
            case SMALL:
                return "w500";
            case LARGE:
                return "w780";
            case ORIGINAL:
                return "original";
            default:
                return null;
        }
    }

    private static String getFullUrl(String path, String size) {
        return MOVIE_POSTER_BASE_URL + size + "/" + path;
    }
}
