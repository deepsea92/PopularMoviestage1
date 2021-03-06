package com.sharma.deepak.popularmoviestage1.network;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by deepak on 18-04-2017.
 */

public class NetworkConnection {
    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String YOUTUBE_URL = "https://www.youtube.com/";
    private static final String YOUTUBE_WATCH = "watch";
    private static final String YOUTUBE_PARAM = "v";
    private static final String API_KEY = "b27eb13363d494dea756f2639056df68";
    private static final String API_PARAM = "api_key";

    public static URL buildUrl(String preference) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(preference)
                .appendQueryParameter(API_PARAM, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static URL videoOrReviewUrl(String preference, String movieId) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(movieId)
                .appendPath(preference)
                .appendQueryParameter(API_PARAM, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Video or Review URI " + url);

        return url;
    }

    public static URL youtubeUrl(String videoId) {
        Uri builtUri = Uri.parse(YOUTUBE_URL).buildUpon()
                .appendPath(YOUTUBE_WATCH)
                .appendQueryParameter(YOUTUBE_PARAM, videoId)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Video or Review URI " + url);

        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}

