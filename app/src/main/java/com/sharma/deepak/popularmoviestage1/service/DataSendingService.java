package com.sharma.deepak.popularmoviestage1.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sharma.deepak.popularmoviestage1.DetailActivity;

/**
 * Created by deepak on 27-05-2017.
 */

public class DataSendingService extends IntentService {
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

    public static void dataService(Context context, String action) {
        Intent dataIntent = new Intent(context, DataSendingService.class);
        dataIntent.setAction(action);
        context.startService(dataIntent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e("intent service", intent.getAction());
        switch (intent.getAction()) {
            case DetailActivity.ACTION_REVIEWS:
                Intent bIntent = new Intent(DetailActivity.ACTION_REVIEWS);
                sendBroadcast(bIntent);
                Log.e("service", "started");

        }

    }
}
