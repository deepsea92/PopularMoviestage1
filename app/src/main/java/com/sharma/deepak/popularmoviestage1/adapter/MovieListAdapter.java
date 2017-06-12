package com.sharma.deepak.popularmoviestage1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sharma.deepak.popularmoviestage1.R;
import com.sharma.deepak.popularmoviestage1.bean.Movie;

import java.util.ArrayList;

/**
 * Created by deepak on 18-04-2017.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieHolderPattern> {
    private ArrayList<Movie> movieListDetails;
    private Context context;
    movieItemClickInterface movieInterface;

    public MovieListAdapter(ArrayList<Movie> movieListDetails, Context context, movieItemClickInterface movieInterface) {
        this.context = context;
        this.movieListDetails = movieListDetails;
        this.movieInterface = movieInterface;
    }

    @Override
    public MovieHolderPattern onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);

        return new MovieHolderPattern(itemView);
    }

    @Override
    public void onBindViewHolder(MovieHolderPattern holder, int position) {
        Movie movie = movieListDetails.get(position);
        holder.movieTitle.setText(movie.getMovieName());
        String movieDetailString = "(" + movie.getLanguage() + ")" + "(" + movie.getVoteAverage() + "/10)";
        holder.movieDetail.setText(movieDetailString);

        Glide
                .with(context)
                .load(movie.getImageThumbnail())
                .error(R.drawable.movie_error)
                .centerCrop()
                .crossFade()
                .placeholder(R.drawable.movie_default)
                .into(holder.movieImage);
    }

    @Override
    public int getItemCount() {
        return movieListDetails.size();
    }

    class MovieHolderPattern extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView movieImage;
        TextView movieTitle, movieDetail;

        public MovieHolderPattern(View itemView) {
            super(itemView);
            movieImage = (ImageView) itemView.findViewById(R.id.iv_movie);
            movieTitle = (TextView) itemView.findViewById(R.id.tv_movie_name);
            movieDetail = (TextView) itemView.findViewById(R.id.tv_movie_detail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            movieInterface.movieClick(position);
        }
    }


    public interface movieItemClickInterface {
        public void movieClick(int position);
    }
}
