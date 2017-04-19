package com.sharma.deepak.popularmoviestage1.network;

import com.sharma.deepak.popularmoviestage1.bean.Movie;

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
                String poster_path = jsonMovieObject.getString("poster_path");
                String release_date = jsonMovieObject.getString("release_date");
                String original_title = jsonMovieObject.getString("original_title");
                double vote_average = jsonMovieObject.getDouble("vote_average");
                int vote_count = jsonMovieObject.getInt("vote_count");
                String original_language = jsonMovieObject.getString("original_language");
                String overview = jsonMovieObject.getString("overview");

                Movie movieDataObject = new Movie(original_title, poster_path, overview, release_date, original_language, vote_average, vote_count);
                movieList.add(movieDataObject);
            }
        } catch (JSONException e) {
            movieList = null;
            e.printStackTrace();
        }
        return movieList;
    }


}
