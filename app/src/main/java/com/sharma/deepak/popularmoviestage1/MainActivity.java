package com.sharma.deepak.popularmoviestage1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.sharma.deepak.popularmoviestage1.adapter.MovieListAdapter;
import com.sharma.deepak.popularmoviestage1.bean.Movie;
import com.sharma.deepak.popularmoviestage1.network.MovieDataJSONResponse;
import com.sharma.deepak.popularmoviestage1.network.NetworkConnection;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.MovieItemClickInterface {
    private RecyclerView mMovieRecyclerView;
    private ProgressBar mProgressBar;
    private LinearLayout mErrorLinearLayout;

    private static final String POPULAR_PARAM = "popular";
    private static final String TOP_RATED_PARAM = "top_rated";
    private GridLayoutManager gridLayout;
    ArrayList<Movie> movieListData;
    static final String MOVIE_DATA_PASSED_KEY = "moviePassedData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Popular Movies");
        initView();
        makeConnection(POPULAR_PARAM);

    }

    // Method to initialise the ui components and adapters.
    private void initView() {


        mMovieRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_list);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_load_back);
        mErrorLinearLayout = (LinearLayout) findViewById(R.id.ll_error);
    }

    //Method to take preference value and start asynctask
    private void makeConnection(String preference) {
        URL url = NetworkConnection.buildUrl(preference);
        new MovieDataTask().execute(url);
    }

    //Method called to show error view and hide data
    private void showErrorView() {
        mMovieRecyclerView.setVisibility(View.GONE);
        mErrorLinearLayout.setVisibility(View.VISIBLE);

    }

    //Method called to show movie data and hide error
    private void showDataView() {
        mErrorLinearLayout.setVisibility(View.GONE);
        mMovieRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_popular) {
            makeConnection(POPULAR_PARAM);
            setTitle("Popular Movies");
            return true;
        } else if (id == R.id.action_top_rated) {
            makeConnection(TOP_RATED_PARAM);
            setTitle("Top Rated Movies");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void movieClick(int position) {
        Movie movie = movieListData.get(position);
        Intent detailActivityIntent = new Intent(this, DetailActivity.class);
        detailActivityIntent.putExtra(MOVIE_DATA_PASSED_KEY, movie);
        startActivity(detailActivityIntent);
    }

    private class MovieDataTask extends AsyncTask<URL, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
            mMovieRecyclerView.setVisibility(View.GONE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(URL... params) {
            movieListData = null;
            try {
                String responseData = NetworkConnection.getResponseFromHttpUrl(params[0]);
                Log.e("JSON REsponse", responseData);
                movieListData = MovieDataJSONResponse.movieData(responseData);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieListData;
        }


        @Override
        protected void onPostExecute(ArrayList<Movie> movieList) {
            super.onPostExecute(movieList);
            mProgressBar.setVisibility(View.INVISIBLE);
            if (movieList != null) {
                showDataView();
                gridLayout = new GridLayoutManager(MainActivity.this, 2);


                mMovieRecyclerView.setHasFixedSize(true);
                mMovieRecyclerView.setLayoutManager(gridLayout);

                MovieListAdapter rcAdapter = new MovieListAdapter(movieList, MainActivity.this, MainActivity.this);
                mMovieRecyclerView.setAdapter(rcAdapter);


            } else {
                showErrorView();
            }
        }
    }

}
