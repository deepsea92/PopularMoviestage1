package com.sharma.deepak.popularmoviestage1.network;

import com.sharma.deepak.popularmoviestage1.bean.Movie;
import com.sharma.deepak.popularmoviestage1.bean.Reviews;
import com.sharma.deepak.popularmoviestage1.bean.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by deepak on 18-04-2017.
 */

public class MovieDataJSONResponse {

    public static ArrayList<Movie> movieData(String jsonData) {
        ArrayList<Movie> movieList = new ArrayList<Movie>();

        try {
            JSONObject rootObject = new JSONObject(jsonData);
            JSONArray resultArray = rootObject.getJSONArray("results");
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject jsonMovieObject = resultArray.getJSONObject(i);
                String id = jsonMovieObject.getString("id");
                String poster_path = jsonMovieObject.getString("poster_path");
                String release_date = jsonMovieObject.getString("release_date");
                String original_title = jsonMovieObject.getString("original_title");
                double vote_average = jsonMovieObject.getDouble("vote_average");
                int vote_count = jsonMovieObject.getInt("vote_count");
                String original_language = jsonMovieObject.getString("original_language");
                String overview = jsonMovieObject.getString("overview");
                String backdrop_path = jsonMovieObject.getString("backdrop_path");
                Movie movieDataObject = new Movie(id, original_title, poster_path, overview, release_date, original_language, vote_average, vote_count, backdrop_path);
                movieList.add(movieDataObject);
            }
        } catch (JSONException e) {
            movieList = null;
            e.printStackTrace();
        }
        return movieList;
    }

    public static ArrayList<Reviews> reviewData(String jsonData) {
        ArrayList<Reviews> reviewList = new ArrayList<Reviews>();


        try {
            JSONObject rootObject = new JSONObject(jsonData);
            JSONArray reviewArray = rootObject.getJSONArray("results");

            for (int i = 0; i < reviewArray.length(); i++) {
                JSONObject reviewJSONObject = reviewArray.getJSONObject(i);
                String author = reviewJSONObject.getString("author");
                String content = reviewJSONObject.getString("content");

                Reviews review = new Reviews(author, content);
                reviewList.add(review);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviewList;
    }

    public static ArrayList<Trailer> trailerData(String jsonData) {
        ArrayList<Trailer> trailerList = new ArrayList<Trailer>();


        try {
            JSONObject rootObject = new JSONObject(jsonData);
            JSONArray trailerJSONArray = rootObject.getJSONArray("results");

            for (int i = 0; i < trailerJSONArray.length(); i++) {
                JSONObject trailerJSONObject = trailerJSONArray.getJSONObject(i);
                String key = trailerJSONObject.getString("key");
                String type = trailerJSONObject.getString("type");
                String name = trailerJSONObject.getString("name");

                Trailer trailer = new Trailer(key, type, name);
                trailerList.add(trailer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trailerList;
    }

}
