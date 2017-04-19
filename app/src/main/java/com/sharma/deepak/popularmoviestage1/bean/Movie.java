package com.sharma.deepak.popularmoviestage1.bean;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by deepak on 18-04-2017.
 */

public class Movie implements Serializable {
    private String movieName, imageThumbnail, overview, releaseDate, language;
    private double voteAverage;
    private int voteCount;

    private static final String BASE_URL = "http://image.tmdb.org/t/p/w185";

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(String imageThumbnail) {
        this.imageThumbnail = moviePath(imageThumbnail);
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public Movie(String movieName, String imageThumbnail, String overview, String releaseDate, String language, double voteAverage, int voteCount) {
        this.movieName = movieName;
        this.imageThumbnail = moviePath(imageThumbnail);

        this.overview = overview;
        this.releaseDate = releaseDate;
        this.language = language;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    private final String moviePath(String addr) {

        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(addr)
                .build();
        return builtUri.toString();
    }
}
