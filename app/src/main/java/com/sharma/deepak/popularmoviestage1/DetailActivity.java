package com.sharma.deepak.popularmoviestage1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sharma.deepak.popularmoviestage1.adapter.ReviewAdapter;
import com.sharma.deepak.popularmoviestage1.adapter.TrailerAdapter;
import com.sharma.deepak.popularmoviestage1.bean.Movie;
import com.sharma.deepak.popularmoviestage1.bean.Reviews;
import com.sharma.deepak.popularmoviestage1.bean.Trailer;
import com.sharma.deepak.popularmoviestage1.network.MovieDataJSONResponse;
import com.sharma.deepak.popularmoviestage1.service.DataSendingService;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements TrailerAdapter.TrailerItemClickInterface {
    private ImageView mMovieImage;
    private TextView mMovieNameTextView, mMovieLanguageTextView, mMovieReleaseDateTextView, mOverViewTextView, mUserRatingTextView;
    public static final String ACTION_TRAILERS = "trailer_action";
    public static final String ACTION_REVIEWS = "review_action";
    public static final String REVIEWS_STRING = "reviews";
    public static final String TRAILERS_STRING = "videos";

    private RecyclerView mRvReviewList, mRvTrailerList;
    private ProgressBar mPbReview, mPbTrailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();

        Intent detailIntent = getIntent();
        if (detailIntent != null) {
            Movie movie = (Movie) detailIntent.getSerializableExtra(MainActivity.MOVIE_DATA_PASSED_KEY);
            assignDataToView(movie);
            DataSendingService.dataService(this, ACTION_REVIEWS, movie.getId());
            DataSendingService.dataService(this, ACTION_TRAILERS, movie.getId());
        }


    }

    //Method to assign data on UI
    private void assignDataToView(Movie movie) {
        String movieName = movie.getMovieName();
        String language = movie.getLanguage();
        String overView = movie.getOverview();
        String releaseDate = movie.getReleaseDate();
        String imagePath = movie.getImageThumbnail();
        String movieId = movie.getId();
        Log.e("Movie path", imagePath);

        double rating = movie.getVoteAverage();
        int voteCount = movie.getVoteCount();

        Glide
                .with(this)
                .load(imagePath)
                .crossFade()
                .error(R.drawable.movie_error)
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

        mRvReviewList = (RecyclerView) findViewById(R.id.rv_review_list);
        mRvTrailerList = (RecyclerView) findViewById(R.id.rv_trailer_list);

        mPbReview = (ProgressBar) findViewById(R.id.pb_review);
        mPbTrailer = (ProgressBar) findViewById(R.id.pb_trailer);
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
            String action = intent.getAction();

            if (action.equals(ACTION_TRAILERS)) {
                String trailerJSONString = intent.getStringExtra(DataSendingService.TRAILER_JSON);
                setUpTrailerData(trailerJSONString);
            } else if (action.equals(ACTION_REVIEWS)) {
                String reviewJSONString = intent.getStringExtra(DataSendingService.REVIEW_JSON);
                setUpReviewData(reviewJSONString);
            }
        }
    };

    private void setUpReviewData(String reviewJSONString) {
        Log.e("broadcast", "review broadcast received");
        mPbReview.setVisibility(View.INVISIBLE);
        ArrayList<Reviews> reviewsArrayList = MovieDataJSONResponse.reviewData(reviewJSONString);

        LinearLayoutManager reviewLinearLayoutManager = new LinearLayoutManager(this);
        mRvReviewList.setHasFixedSize(true);
        mRvReviewList.setLayoutManager(reviewLinearLayoutManager);
        ReviewAdapter reviewAdapter = new ReviewAdapter(reviewsArrayList, this);
        mRvReviewList.setAdapter(reviewAdapter);

    }

    private void setUpTrailerData(String trailerJSONString) {
        Log.e("broadcast", "trailer broadcast received");
        mPbTrailer.setVisibility(View.INVISIBLE);
        ArrayList<Trailer> trailerArrayList = MovieDataJSONResponse.trailerData(trailerJSONString);

        LinearLayoutManager trailerLinearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRvTrailerList.setHasFixedSize(true);
        mRvTrailerList.setLayoutManager(trailerLinearLayoutManager);
        TrailerAdapter trailerAdapter = new TrailerAdapter(trailerArrayList, this, this);
        mRvTrailerList.setAdapter(trailerAdapter);
    }

    @Override
    public void trailerClick(int position) {

    }
}
