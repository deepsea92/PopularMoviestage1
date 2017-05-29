package com.sharma.deepak.popularmoviestage1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sharma.deepak.popularmoviestage1.bean.Movie;
import com.sharma.deepak.popularmoviestage1.service.DataSendingService;

public class DetailActivity extends AppCompatActivity {
    private ImageView mMovieImage;
    private TextView mMovieNameTextView, mMovieLanguageTextView, mMovieReleaseDateTextView, mOverViewTextView, mUserRatingTextView;
    public static final String ACTION_TRAILERS = "trailer_action";
    public static final String ACTION_REVIEWS = "review_action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();

        Intent detailIntent = getIntent();
        if (detailIntent != null) {
            Movie movie = (Movie) detailIntent.getSerializableExtra(MainActivity.MOVIE_DATA_PASSED_KEY);
            assignUiToView(movie);
        }

        DataSendingService.dataService(this, ACTION_REVIEWS);

    }

    //Method to assign data on UI
    private void assignUiToView(Movie movie) {
        String movieName = movie.getMovieName();
        String language = movie.getLanguage();
        String overView = movie.getOverview();
        String releaseDate = movie.getReleaseDate();
        String imagePath = movie.getImageThumbnail();
        Log.e("Movie path", imagePath);
        double rating = movie.getVoteAverage();
        int voteCount = movie.getVoteCount();


        Glide
                .with(this)
                .load(imagePath)
                .crossFade()
                .placeholder(R.drawable.movie_default)
                .into(mMovieImage);

        mOverViewTextView.setText(overView);
        mMovieLanguageTextView.setText(language);
        mMovieReleaseDateTextView.setText(releaseDate);
        mMovieNameTextView.setText(movieName);
        mUserRatingTextView.setText("(" + rating + ") " + voteCount + " votes");


    }

    // Method to initialise all UI views
    private void initView() {
        mMovieImage = (ImageView) findViewById(R.id.iv_movie_image);
        mMovieNameTextView = (TextView) findViewById(R.id.tv_movie_name);
        mMovieLanguageTextView = (TextView) findViewById(R.id.tv_language);
        mMovieReleaseDateTextView = (TextView) findViewById(R.id.tv_release_date);
        mUserRatingTextView = (TextView) findViewById(R.id.tv_rating_description);
        mOverViewTextView = (TextView) findViewById(R.id.tv_overview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(ACTION_REVIEWS);
        iFilter.addAction(ACTION_TRAILERS);
        registerReceiver(mDetailActivityBroadcast, iFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mDetailActivityBroadcast);
    }

    BroadcastReceiver mDetailActivityBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("broadcast", "broadcast recived");
            String action = intent.getAction();
            if (action.equals(ACTION_TRAILERS))
                setUpTrailerData();
            else if (action.equals(ACTION_REVIEWS))
                setUpReviewData();
        }
    };

    private void setUpReviewData() {
        Log.e("broadcast", "broadcast recived");
    }

    private void setUpTrailerData() {
    }
}
