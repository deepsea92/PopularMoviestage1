package com.sharma.deepak.popularmoviestage1.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sharma.deepak.popularmoviestage1.DetailActivity;
import com.sharma.deepak.popularmoviestage1.network.NetworkConnection;

import java.io.IOException;

/**
 * Created by deepak on 27-05-2017.
 */

public class DataSendingService extends IntentService {
    public static final String REVIEW_JSON = "review_json";
    public static final String TRAILER_JSON = "trailer_json";
    public static final String MOVIE_ID_EXTRA = "movieId";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DataSendingService(String name) {
        super(name);
    }

    public DataSendingService() {
        super("");
    }

    public static void dataService(Context context, String action, String movieId) {
        Intent dataIntent = new Intent(context, DataSendingService.class);
        dataIntent.setAction(action);
        dataIntent.putExtra(MOVIE_ID_EXTRA, movieId);
        context.startService(dataIntent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e("intent service", intent.getAction());
        String movieId = intent.getStringExtra(MOVIE_ID_EXTRA);
        switch (intent.getAction()) {
            case DetailActivity.ACTION_REVIEWS:
                String reviewJSONString = null;
                try {
                    reviewJSONString = NetworkConnection
                            .getResponseFromHttpUrl(NetworkConnection.videoOrReviewUrl(DetailActivity.REVIEWS_STRING, movieId));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent reviewIntent = new Intent(DetailActivity.ACTION_REVIEWS);
                reviewIntent.putExtra(REVIEW_JSON, reviewJSONString);
                sendBroadcast(reviewIntent);
                Log.e("service", "started");
                break;

            case DetailActivity.ACTION_TRAILERS:
                String trailerJSONString = null;
                try {
                    trailerJSONString = NetworkConnection
                            .getResponseFromHttpUrl(NetworkConnection.videoOrReviewUrl(DetailActivity.TRAILERS_STRING, movieId));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent trailerIntent = new Intent(DetailActivity.ACTION_TRAILERS);
                trailerIntent.putExtra(TRAILER_JSON, trailerJSONString);
                sendBroadcast(trailerIntent);
                Log.e("service", "started");
                break;
        }

    }
}
